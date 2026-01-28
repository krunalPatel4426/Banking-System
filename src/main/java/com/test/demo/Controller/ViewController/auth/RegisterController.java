package com.test.demo.Controller.ViewController.auth;

import com.test.demo.Repository.UserRepository;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class RegisterController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerPage(){
        return "RegisterPages/register";
    }

    @PostMapping("/perform_register")
    public String performRegister(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model,
            RedirectAttributes redirectAttributes
    ){
        System.out.println("working 1.");
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setBalance(new BigDecimal("0"));
        try{
            System.out.println("Working");
            userRepository.save(user);
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong. Please try again later.");
            return "redirect:/register";
        }

        return "redirect:/login?success=registered";
    }
}
