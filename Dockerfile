# Use an official Maven image to build the application
FROM maven AS build

# Set the working directory for the build
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY EcommerceWebsite/pom.xml .

# Download dependencies (This will be cached unless pom.xml changes)
RUN mvn dependency:go-offline

# Copy the source code
COPY EcommerceWebsite/src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Install dockerize
RUN apt-get update && apt-get install -y wget \
  && wget -qO /usr/local/bin/dockerize https://github.com/jwilder/dockerize/releases/download/v0.8.0/dockerize-linux-amd64-v0.8.0.tar.gz \
  && chmod +x /usr/local/bin/dockerize


# Set the working directory for the runtime
WORKDIR /app

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/EcommerceWebsite-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Use dockerize to wait for MySQL and then start the application
ENTRYPOINT ["dockerize", "-wait", "tcp://mysql-db:3306", "-timeout", "60s", "java", "-jar", "app.jar"]
