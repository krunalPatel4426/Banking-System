package com.test.demo.config.CustomError;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg){
        super(msg);
    }
}
