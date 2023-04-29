package com.example.hctbank.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBalanceDTO {
    Long account_id;
    double aval_bal;
}
