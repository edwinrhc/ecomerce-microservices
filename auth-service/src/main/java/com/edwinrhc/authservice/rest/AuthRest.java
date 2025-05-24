package com.edwinrhc.authservice.rest;


import com.edwinrhc.authservice.dto.user.CreateUserDTO;
import com.edwinrhc.authservice.dto.user.LoginDTO;
import com.edwinrhc.authservice.dto.user.UserListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/user")
public interface AuthRest {

    @Operation(summary = "Registro de usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping(path="/signup")
    ResponseEntity<String> createUser(@RequestBody @Valid CreateUserDTO dto);

    @Operation(summary = "Login de usuario", description = "Autentica al usuario y devuelve un JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token generado"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping(path="/login")
    ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO);

    @GetMapping("/get")
    ResponseEntity<List<UserListDTO>> getAllUser();


}
