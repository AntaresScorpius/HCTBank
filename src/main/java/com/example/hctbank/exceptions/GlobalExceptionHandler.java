package com.example.hctbank.exceptions;

import com.example.hctbank.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleCustomException(InvalidException ex){
        return new ErrorResponse(ex.getMessage(),ex.getError_code());
    }

    @ExceptionHandler(ParamRequiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCustomException(ParamRequiredException ex){
        return new ErrorResponse(ex.getMessage(),ex.getError_code());
    }

    @ExceptionHandler(FailedTransactionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCustomException(FailedTransactionException ex){
        return new ErrorResponse(ex.getMessage(),ex.getError_code());
    }



}
