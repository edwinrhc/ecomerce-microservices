package com.edwinrhc.authservice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO {
    private Long id;
    private String name;
    private String contactNumber;
    private String email;
    private String role;
    private String status;


}