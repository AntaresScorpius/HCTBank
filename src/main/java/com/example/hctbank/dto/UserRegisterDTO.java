package com.example.hctbank.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class UserRegisterDTO {

    String name;
    Long customer_id;
    Long account_id;
    Double balance;

}
