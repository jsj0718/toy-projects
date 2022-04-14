package com.elevenhelevenm.practice.board.repository;

import com.elevenhelevenm.practice.board.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
