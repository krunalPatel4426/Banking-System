package com.test.demo.Controller.APIController.user;

import com.test.demo.Service.auth.UserService;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping
    public User addUser(@RequestBody User user){
        return userService.createUser(user);
    }

//    @PostMapping
//    public List<User> addUser(@RequestBody List<User> users){
//        return userService.createAllUser(users);
//    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public String removeUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "User deleted Successfully.";
    }

    @GetMapping("/profile")
    public String userProfile() {
        return "Welcome to the User Profile (You are Authenticated!)";
    }
}
