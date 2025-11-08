package com.example.linkedOut.dto;

import lombok.Data;

@Data
public class UpdateUserDto  implements GenericDto{
    private String fullName;
    private String headline;
    private String about;
}
