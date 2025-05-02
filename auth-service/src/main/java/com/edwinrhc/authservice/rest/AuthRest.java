package com.edwinrhc.authservice.rest;


import com.edwinrhc.authservice.dto.user.CreateUserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/users")
public interface AuthRest {

    @PostMapping(value="/add")
    ResponseEntity<String> createUser(@RequestBody @Valid CreateUserDTO dto);


}
