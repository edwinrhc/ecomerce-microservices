package com.edwinrhc.authservice.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateUserDTO {

    @Column(unique=true, nullable=false,length=100)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "El numero  no puede estar vacío")
    private String contactNumber;

    @Email
    @Column(unique=true, nullable=false,length=100)
    @NotEmpty(message="El email no puede estar en blanco")
    private String email;

    @Column(nullable=false)
    @NotBlank(message = "El password no puede estar vacío")
    private String password;

    @Column(nullable=false)
    @NotBlank(message = "El role no puede estar vacío")
    private String role;

    private boolean enabled = true;
}
