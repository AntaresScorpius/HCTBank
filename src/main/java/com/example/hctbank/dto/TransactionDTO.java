package com.example.hctbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TransactionDTO {
    Long acc_id;
    Long to_acc_id;
    String type;
    double amount;
}
