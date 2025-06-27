package com.example.TournamentSetup.controllers;

import com.example.TournamentSetup.models.User;
import com.example.TournamentSetup.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            // look for the user in DB
            Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }

            User foundUser = optionalUser.get();

            // JSON response
            Map<String, Object> response = new HashMap<>();
            response.put("username", foundUser.getUsername());
            response.put("role", foundUser.getRole());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}