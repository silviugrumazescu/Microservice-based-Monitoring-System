package com.example.devicesmicroservice.repository;

import com.example.devicesmicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByUsername(String Username);

    Optional<User> findFirstByUsername(String Username);
}
