package com.example.TournamentSetup.repositories;

import com.example.TournamentSetup.models.User;

import com.example.TournamentSetup.models.Role;
import com.example.TournamentSetup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // 🔹 Add this method

    Optional<User> findByEmail(String email); // Fetch users by email

    List<User> findByRole(Role role); // Fetch users by role
}