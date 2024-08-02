package com.ecommerce.EcommerceWebsite.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Table(name = "user_table")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private PasswordResetToken passwordResetTokens;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cart> carts;

    public User(String email, String username, String encodedPassword, String fullname, String address, int phone) {
        this.email = email;
        this.username = username;
        this.password = encodedPassword;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password + ", fullname=" + fullname + "]";
    }
}