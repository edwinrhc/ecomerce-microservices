package com.edwinrhc.authservice.service;


import com.edwinrhc.authservice.dto.user.CreateUserDTO;
import com.edwinrhc.authservice.dto.user.UpdateUserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<String> signUp(CreateUserDTO createUserDTO);
    ResponseEntity<String> updateUser(UpdateUserDTO updateUserDTO);
    ResponseEntity<String> deleteUser(String userId);




}
