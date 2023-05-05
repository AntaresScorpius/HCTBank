package com.example.hctbank.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionPdfDTO {
    Date date;
    String Description;
    Double in;
    Double out;
    Double balance;

}
