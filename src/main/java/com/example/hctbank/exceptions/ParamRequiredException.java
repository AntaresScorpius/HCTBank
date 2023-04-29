package com.example.hctbank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ParamRequiredException extends RuntimeException {
    String message = "Required Query parameter's are not provided!.";
    String error_code = "HCTB400";
}
