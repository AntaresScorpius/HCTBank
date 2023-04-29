package com.example.hctbank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FailedTransactionException extends RuntimeException{
    String message = "Provided Details are Invalid!..";
    String error_code = "HCTB400";

}
