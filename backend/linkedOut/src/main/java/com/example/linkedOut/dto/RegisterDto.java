package com.example.linkedOut.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class RegisterDto  implements GenericDto{
    @Email 
    @NotBlank
    private String email;

    @NotBlank 
    @Size(min = 6)
    private String password;

    @NotBlank
    private String fullName;

    private String headline;
}
