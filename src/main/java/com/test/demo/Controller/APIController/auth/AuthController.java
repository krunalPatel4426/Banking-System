package com.test.demo.Controller.APIController.auth;

import com.test.demo.Repository.UserRepository;
import com.test.demo.Service.auth.JwtService;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        System.out.println("Generated Token : " + jwtService.generateToken(user.getUsername()));
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // 1. We just pass the Email/Pass to the Manager.
        // The Manager will internally call loadUserByUsername(email).
        Authentication authentication;
        if(user.getEmail() != null){
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
        }else{
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        }

        user = userRepository.findByUsernameOrEmailAndIsDeleted(user.getUsername(), user.getEmail(), 0).orElseThrow(() -> new UsernameNotFoundException("Username not found."));

        // 2. If we reach here, it means the Manager found the user AND the password matched.
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @GetMapping("/check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam String email){
        return userRepository.findUserByEmailAndIsDeleted(email, 0).isPresent();
    }

    @GetMapping("/check-username")
    @ResponseBody
    public boolean checkUsername(@RequestParam String username){
        return userRepository.findByUsernameAndIsDeleted(username, 0).isPresent();
    }
}
