package com.ecommerce.EcommerceWebsite.service;

import com.ecommerce.EcommerceWebsite.dto.UserDto;
import com.ecommerce.EcommerceWebsite.model.User;

public interface UserService {
    User findByUsername(String username);

    User save(UserDto userDto);
}

// @Service
// public class UserService {

//     @Autowired
//     private UserRepository userRepository;

//     private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//     public String hashPassword(String plainPassword) {
//         return passwordEncoder.encode(plainPassword);
//     }

//     public boolean checkPassword(String plainPassword, String hashedPassword) {
//         return passwordEncoder.matches(plainPassword, hashedPassword);
//     }

//     public void saveUser(User user) {
//         String hashedpassword = hashPassword(user.getPassword());
//         user.setPassword(hashedpassword);
//         userRepository.save(user);
//     }
// //    public User updateUser(Long id,User user) {
// //        return userRepository.save(user);
// //    }

//     public boolean login(String email,String password){
//         User user = userRepository.findByEmail(email);
//         if (user != null) {
//             return checkPassword(password, user.getPassword());
//         }
//         return false;
//     }

//     public boolean deleteUser(Long id) {
//         try {
//             userRepository.deleteById(id);
//             return true;
//         } catch (EmptyResultDataAccessException e) {
//             return false;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }


// }
