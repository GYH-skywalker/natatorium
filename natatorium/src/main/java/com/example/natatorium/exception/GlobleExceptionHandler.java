package com.example.natatorium.exception;


import com.example.natatorium.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobleExceptionHandler {


    @ExceptionHandler(serviceException.class)
    @ResponseBody
    public Result handle(serviceException se){
        return Result.error(se.getCode(),se.getMessage());
    }
}
