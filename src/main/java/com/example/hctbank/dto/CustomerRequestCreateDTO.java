package com.example.hctbank.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestCreateDTO {
    String name;
    String Country;
    String city;
    String AddressLane;
    Long pincode;
    Long phone;
    String email;
}
