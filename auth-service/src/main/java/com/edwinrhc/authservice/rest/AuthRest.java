package com.edwinrhc.authservice.rest;


import com.edwinrhc.authservice.dto.user.CreateUserDTO;
import com.edwinrhc.authservice.dto.user.LoginDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
public interface AuthRest {

    @PostMapping(path="/signup")
    ResponseEntity<String> createUser(@RequestBody @Valid CreateUserDTO dto);

    @PostMapping(path="/login")
    ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO);


}
