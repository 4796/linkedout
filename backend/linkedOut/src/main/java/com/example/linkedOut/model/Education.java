package com.example.linkedOut.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {
    private String school;
    private String degree;
    private String from;
    private String to;
    private String description;
}
