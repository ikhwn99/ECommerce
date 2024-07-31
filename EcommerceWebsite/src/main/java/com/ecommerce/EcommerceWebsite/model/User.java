package com.ecommerce.EcommerceWebsite.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;
    private String fullname;
    private String address;
    private int phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cart> carts;

    public User(String email, String username, String password, String fullname, String address, int phone) {
        super();
        this.email = email;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
       }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password + ", fullname=" + fullname + "]";
    }
}

// @Table(name = "user")
// @Entity
// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
// public class User {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     private String name;
//     private String email;
//     private String password;
//     private String address;
//     private int phone;

// }

