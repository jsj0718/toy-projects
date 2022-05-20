package com.elevenhelevenm.practice.board.domain.grid.repository;

import com.elevenhelevenm.practice.board.domain.grid.model.GridData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GridDataRepository extends JpaRepository<GridData, Long> {
}
