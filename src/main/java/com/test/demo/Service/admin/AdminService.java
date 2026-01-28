package com.test.demo.Service.admin;

import com.test.demo.Repository.UserRepository;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
