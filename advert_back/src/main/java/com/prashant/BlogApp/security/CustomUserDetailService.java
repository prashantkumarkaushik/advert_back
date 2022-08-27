package com.prashant.BlogApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prashant.BlogApp.entities.User;
import com.prashant.BlogApp.exceptions.ResourceNotFoundException;
import com.prashant.BlogApp.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // loading user from the database by username
        User user = userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User ", " email: " + username, 0));

        return user;
    }
    
}
