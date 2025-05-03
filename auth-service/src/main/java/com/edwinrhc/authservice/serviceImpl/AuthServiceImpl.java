package com.edwinrhc.authservice.serviceImpl;

import com.edwinrhc.authservice.constants.AuthConstants;
import com.edwinrhc.authservice.dto.user.CreateUserDTO;
import com.edwinrhc.authservice.dto.user.UpdateUserDTO;
import com.edwinrhc.authservice.entity.User;
import com.edwinrhc.authservice.repository.UserRepository;
import com.edwinrhc.authservice.service.AuthService;
import com.edwinrhc.authservice.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> signUp(CreateUserDTO createUserDTO) {
        log.info("createUser : {}", createUserDTO);
        try{
            User validEmail = userRepository.findByEmail(createUserDTO.getEmail());
            if(Objects.isNull(validEmail)){
                User user = modelMapper.map(createUserDTO, User.class);
                user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
                User savedUser = userRepository.save(user);
                CreateUserDTO result = modelMapper.map(savedUser, CreateUserDTO.class);
                return AuthUtils.getResponseEntity(AuthConstants.SUCCESS, HttpStatus.OK);
            }else{
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(AuthConstants.EMAIL_ALREADY_EXISTS);

            }
        } catch (Exception e){
           log.error("Error al crear el usuario",e);
           e.printStackTrace();
        }
        return  AuthUtils.getResponseEntity(AuthConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(UpdateUserDTO updateUserDTO) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteUser(String userId) {
        return null;
    }
}
