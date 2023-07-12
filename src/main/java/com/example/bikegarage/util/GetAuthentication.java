package com.example.bikegarage.util;

import com.example.bikegarage.exception.ForbiddenException;
import com.example.bikegarage.exception.UsernameNotFoundException;
import com.example.bikegarage.model.User;
import com.example.bikegarage.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetAuthentication {

    private final UserRepository userRepository;

    public GetAuthentication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getLoggedInUser(String role, String message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            boolean hasAuthority = authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
            if (!hasAuthority) {
                throw new ForbiddenException(message);
            }
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

}
