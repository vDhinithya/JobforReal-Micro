package com.jobportal.identity.service;

import com.jobportal.identity.dto.AuthRequest;
import com.jobportal.identity.dto.UserRequest;
import com.jobportal.identity.entity.User;
import com.jobportal.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String register(UserRequest request) {
        // 1. Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered!");
        }

        // 2. Map DTO to Entity
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                // 3. Hash the password before saving!
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole().toUpperCase() : "APPLICANT")
                .createdAt(LocalDateTime.now())
                .build();

        // 4. Save to MongoDB
        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(AuthRequest request) {
        // 1. Find the user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with this email"));

        // 2. Check if the raw password matches the hashed password in the DB
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        // 3. Generate and return the JWT Token
        return jwtService.generateToken(user.getEmail(), user.getRole());
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}