package com.elevenhelevenm.practice.board.domain.user.repository;


import com.elevenhelevenm.practice.board.domain.user.model.UserV2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryV2<T extends UserV2> extends JpaRepository<T, Long> {
    Optional<T> findByUsername(String username);

    Optional<T> findByEmail(String Email);
}
