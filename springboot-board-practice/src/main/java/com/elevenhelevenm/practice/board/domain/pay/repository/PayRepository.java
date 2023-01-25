package com.elevenhelevenm.practice.board.domain.pay.repository;

import com.elevenhelevenm.practice.board.domain.pay.model.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay, Long> {
}
