package com.edwinrhc.authservice.restImpl;

import com.edwinrhc.authservice.constants.AuthConstants;
import com.edwinrhc.authservice.dto.user.CreateUserDTO;
import com.edwinrhc.authservice.dto.user.LoginDTO;
import com.edwinrhc.authservice.dto.user.UserListDTO;
import com.edwinrhc.authservice.rest.AuthRest;
import com.edwinrhc.authservice.service.AuthService;
import com.edwinrhc.authservice.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthRestImpl implements AuthRest {

    @Autowired
    AuthService authService;

    @Override
    public ResponseEntity<String> createUser(CreateUserDTO dto) {
       try{
           return authService.signUp(dto);
       }catch(Exception ex){
           ex.printStackTrace();
       }
       return AuthUtils.getResponseEntity(AuthConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(LoginDTO loginDTO) {
        try{
            return authService.login(loginDTO);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return AuthUtils.getResponseEntity(AuthConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserListDTO>> getAllUser() {
        try{
            return authService.listUsers();
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<List<UserListDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
