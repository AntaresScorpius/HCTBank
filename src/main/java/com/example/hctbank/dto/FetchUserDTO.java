package com.example.hctbank.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class FetchUserDTO {
    String name;
    String email;
    Long phone;
    Long customer_id;
    Long account_id;
    Date created_on;
}
