package com.test.demo.config.CustomError;

public class InsufficientBalance extends RuntimeException{
    public InsufficientBalance(String msg){
        super(msg);
    }
}
