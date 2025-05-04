package com.edwinrhc.authservice.serviceImpl;

import com.edwinrhc.authservice.constants.AuthConstants;
import com.edwinrhc.authservice.dto.user.CreateUserDTO;
import com.edwinrhc.authservice.dto.user.LoginDTO;
import com.edwinrhc.authservice.dto.user.UpdateUserDTO;
import com.edwinrhc.authservice.dto.user.UserListDTO;
import com.edwinrhc.authservice.entity.User;
import com.edwinrhc.authservice.jwt.CustomerUsersDetailsService;
import com.edwinrhc.authservice.jwt.JwtFilter;
import com.edwinrhc.authservice.jwt.JwtUtil;
import com.edwinrhc.authservice.repository.PasswordResetTokenRepository;
import com.edwinrhc.authservice.repository.UserRepository;
import com.edwinrhc.authservice.service.AuthService;
import com.edwinrhc.authservice.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> signUp(CreateUserDTO createUserDTO) {
        log.info("createUser : {}", createUserDTO);
        try{
            User validEmail = userRepository.findByEmail(createUserDTO.getEmail());
            if(Objects.isNull(validEmail)){
                User user = modelMapper.map(createUserDTO, User.class);
                user.setRole("user");
                user.setStatus("false");
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
    public ResponseEntity<String> login(LoginDTO logintDTO) {
       log.info("Inside login");
       try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(logintDTO.getEmail(), logintDTO.getPassword()));
            if(authentication.isAuthenticated()){
                if(customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
                                    customerUsersDetailsService.getUserDetail().getRole()) + "\"}",HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>(AuthConstants.INVALID_TOKEN, HttpStatus.BAD_REQUEST);
                }
            }
       }catch (Exception ex){
           log.error("Error al iniciar el login",ex);
       }
        return new ResponseEntity<String>(AuthConstants.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserListDTO>> listUsers() {
        try{
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(userRepository.listUsers(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<UserListDTO>(), HttpStatus.INTERNAL_SERVER_ERROR);
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
