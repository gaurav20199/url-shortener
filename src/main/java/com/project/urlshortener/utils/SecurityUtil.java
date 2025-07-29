package com.project.urlshortener.utils;

import com.project.urlshortener.entity.User;
import com.project.urlshortener.exception.InvalidUserDetails;
import com.project.urlshortener.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUtil {

    private UserRepository userRepository;

    public SecurityUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.isAuthenticated()){
            // email is acting as username
            String email = authentication.getName();
            return userRepository.findByEmail(email);
        }
        return Optional.empty();
    }
}
