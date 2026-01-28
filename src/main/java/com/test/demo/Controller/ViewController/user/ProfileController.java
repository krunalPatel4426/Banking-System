package com.test.demo.Controller.ViewController.user;

import com.test.demo.Repository.UserRepository;
import com.test.demo.config.CustomError.UserNotFoundException;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal){
        String username = principal.getName();
        User user = userRepository.findByUsernameAndIsDeleted(username, 0).orElseThrow(() -> new UserNotFoundException("User not found."));
        model.addAttribute("user", user);
        return "ProfilePages/profile";
    }

    @GetMapping("/change-password")
    public String changePassword() {
        return "ProfilePages/change-password";
    }

    @PostMapping("/perform_change_password")
    public String performChangePassword(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Principal principal,
            RedirectAttributes redirectAttributes
    ){
        User user = userRepository.findByUsernameAndIsDeleted(principal.getName(), 0).orElseThrow(() -> new UserNotFoundException("User Not Found."));
        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            redirectAttributes.addFlashAttribute("errorMessage", "Incorrect old password!");
            return "redirect:/change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "New password does not matches.");
            return "redirect:/change-password";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        redirectAttributes.addAttribute("successMessage", "Password Changed Successfully!");
        return "redirect:/profile";
    }
}
