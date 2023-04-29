package com.example.hctbank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvalidException extends RuntimeException{
    String message = "Provided input's {query params or path params} is/are Invalid!.";
    String error_code = "HCTB404";

}
