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
    private String username;
    private String password;
    private String fullname;

    @Override
    public String toString() {
    return "UserDto [username=" + username + ", password=" + password + ", fullname=" + fullname + "]";
    }
}
