package com.edwinrhc.authservice.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserDTO {

    @Column(unique=true, nullable=false,length=100)
    @NotBlank(message = "El username no puede estar vacío")
    private String username;

    @Column(nullable=false)
    @NotBlank(message = "El password no puede estar vacío")
    private String password;

    @Column(nullable=false)
    @NotBlank(message = "El role no puede estar vacío")
    private String role;

    private boolean enabled = true;
}
