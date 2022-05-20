package com.elevenhelevenm.practice.board.domain.user.repository;

import com.elevenhelevenm.practice.board.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String Email);
}
