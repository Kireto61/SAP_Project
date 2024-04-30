package com.example.diplomenproekt.repositories;

import com.example.diplomenproekt.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByEmail(String email);

    Optional<User> findByEmail(String email);
}
