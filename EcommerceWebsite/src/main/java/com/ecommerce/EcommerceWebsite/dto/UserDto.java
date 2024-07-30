package com.ecommerce.EcommerceWebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String username;
    private String password;
    private String fullname;
    private String address;
    private int phone;

    @Override
    public String toString() {
    return "UserDto [username=" + username + ", email=" + email + ", password=" + password + ", fullname=" + fullname + "]";
    }
}
