package com.ecommerce.EcommerceWebsite.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "user")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private int phone;

}

