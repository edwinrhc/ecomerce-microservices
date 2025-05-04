package com.edwinrhc.authservice.service;


import com.edwinrhc.authservice.dto.user.CreateUserDTO;
import com.edwinrhc.authservice.dto.user.LoginDTO;
import com.edwinrhc.authservice.dto.user.UpdateUserDTO;
import com.edwinrhc.authservice.dto.user.UserListDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthService {

    ResponseEntity<String> signUp(CreateUserDTO createUserDTO);
    ResponseEntity<String> login(LoginDTO logintDTO);
    ResponseEntity<List<UserListDTO>> listUsers();
    ResponseEntity<String> updateUser(UpdateUserDTO updateUserDTO);
    ResponseEntity<String> deleteUser(String userId);


}
