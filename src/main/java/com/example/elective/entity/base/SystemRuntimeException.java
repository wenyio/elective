package com.example.elective.entity.base;

import lombok.Data;

@Data
public class SystemRuntimeException extends RuntimeException {
    private int code;
    private String message;
    public SystemRuntimeException(){
        super("请求异常");
        this.code = 500;
        this.message = "请求异常";
    }
    public SystemRuntimeException(int code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    public SystemRuntimeException(String message){
        super(message);
        this.code = 500;
        this.message = message;
    }
}
