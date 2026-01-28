package com.test.demo.Security.auth;

import com.test.demo.Repository.UserRepository;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmailAndIsDeleted(usernameOrEmail, usernameOrEmail, 0).orElseThrow(() -> new UsernameNotFoundException("user not found with email : " + usernameOrEmail));
        return new UserPrincipal(user);
    }
}
