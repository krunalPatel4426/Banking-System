package com.test.demo.Controller.ViewController.admin;

import com.test.demo.Service.admin.AdminService;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class WebAdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model){
        List<User> allUsers = adminService.getAllUsers();
        model.addAttribute("users", allUsers);
        return "AdminPages/dashboard";
    }
}
