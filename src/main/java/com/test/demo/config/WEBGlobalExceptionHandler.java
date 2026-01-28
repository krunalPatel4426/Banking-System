package com.test.demo.config;

import com.test.demo.config.CustomError.UserNotFoundException;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = Controller.class)
public class WEBGlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public String handleWebUserNotFound(UserNotFoundException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", HttpStatus.NOT_FOUND);
        return "ErrorPages/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception ex, Model model){
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again.");
        model.addAttribute("detailedMessage", ex.getMessage());
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
        return "ErrorPages/error";
    }
}
