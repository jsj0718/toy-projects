package com.elevenhelevenm.practice.board.domain.user.repository;

import com.elevenhelevenm.practice.board.domain.user.model.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {

    Optional<SuperAdmin> findByIp(String ip);
}
