package com.shems.server.service;

import com.shems.server.dao.UserRepository;
import com.shems.server.domain.User;
import com.shems.server.dto.request.UserRequest;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class UserService {

    @Inject
    private UserRepository userRepository;

    public User register(UserRequest request) {
        User toSave = checkForExistingUser(request.getEmail());
        toSave.setName(request.getName());
        toSave.setEmail(request.getEmail());
        toSave.setSignUpDate(Date.from(Instant.now()));
        toSave.setBillingAddress(request.getBillingAddress());
        return userRepository.save(toSave);
    }

    private User checkForExistingUser(String email) {
        return userRepository.findByEmail(email).orElseGet(User::new);
    }

    public User findById(Long customerId) {
        return userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
