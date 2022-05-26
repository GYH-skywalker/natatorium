package com.example.natatorium.exception;

import lombok.Getter;

/**
* 异常处理
* */

@Getter
public class serviceException extends RuntimeException{

    private String code;

    public serviceException(String code,String msg){
        super(msg);
        this.code = code;
    }
}
