package com.example.linkedOut.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {
    private String company;
    private String position;
    private String from; // simple for now
    private String to;
    private String description;
}
