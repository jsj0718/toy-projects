package com.elevenhelevenm.practice.board.domain.log.repository;

import com.elevenhelevenm.practice.board.domain.log.model.LogV2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepositoryV2<T extends LogV2> extends JpaRepository<LogV2, Long> {

}
