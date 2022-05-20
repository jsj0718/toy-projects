package com.elevenhelevenm.practice.board.domain.log.repository;

import com.elevenhelevenm.practice.board.domain.log.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
