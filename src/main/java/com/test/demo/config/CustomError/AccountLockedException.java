package com.test.demo.config.CustomError;

public class AccountLockedException extends RuntimeException{
    public AccountLockedException(String msg){
        super(msg);
    }
}
