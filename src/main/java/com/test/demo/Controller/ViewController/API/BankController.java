package com.test.demo.Controller.ViewController.API;

import com.test.demo.Repository.UserRepository;
import com.test.demo.config.CustomError.UserNotFoundException;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/receiver_account_checker")
    @ResponseBody
    public String receiverAccountChecker(@RequestParam Long id){
        try{
            User user = userRepository.findUserByIdAndIsDeleted(id, 0).orElseThrow(() -> new UserNotFoundException("User not found."));
            return user.getUsername();
        }catch (UserNotFoundException e){
            return e.getMessage();
        }
    }
}
