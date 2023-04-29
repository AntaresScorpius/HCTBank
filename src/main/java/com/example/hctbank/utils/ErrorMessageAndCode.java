package com.example.hctbank.utils;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessageAndCode extends Exception {
    String message;
    String error_code;
}
