package com.example.TournamentSetup.service;

import com.example.TournamentSetup.models.Role;
import com.example.TournamentSetup.models.User;
import com.example.TournamentSetup.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllUsersByRole(Role role) {
        return userRepository.findByRole(role); // Method to fetch users by role
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User  with ID " + id + " not found.");
        }
    }

    @PostConstruct
    public void createAdminUser() {
        Optional<User> adminExists = userRepository.findByEmail("admin@example.com");
        if (adminExists.isEmpty()) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // Set default password
            admin.setRole("ROLE_ADMIN"); // ✅ Ensure it's a String
            userRepository.save(admin);
            System.out.println("Default Admin User Created: Email = admin@example.com, Password = admin123");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}