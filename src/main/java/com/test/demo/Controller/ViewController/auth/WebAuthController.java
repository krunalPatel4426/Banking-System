package com.test.demo.Controller.ViewController.auth;

import com.test.demo.Repository.UserRepository;
import com.test.demo.config.CustomError.UserNotFoundException;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class WebAuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ){
        if(error != null){
            model.addAttribute("errorMessage", "Invalid Username and Password.");
        }
        if(logout != null){
            model.addAttribute("successMessage", "You have logged out successfully.");
        }

        return "AuthPages/login";
    }


    @GetMapping("/dashboard")
    public String dashboardPage(
            Model model,
            Principal principal
    ){
        String username = principal.getName();
        System.out.println("working" + username);
        User user = userRepository.findByUsernameAndIsDeleted(username, 0).orElseThrow(() -> new UserNotFoundException("username not found."));

        model.addAttribute("user", user);
        return "DashBoardPages/dashboard";
    }
}
