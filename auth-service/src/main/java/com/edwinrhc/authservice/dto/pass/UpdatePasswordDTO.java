package com.edwinrhc.authservice.dto.pass;

import jakarta.validation.constraints.NotBlank;

public class UpdatePasswordDTO {

    @NotBlank(message = "El id no puede estar vacío")
    private Long id;

    @NotBlank(message = "El password no puede estar vacío")
    private String password;


}
