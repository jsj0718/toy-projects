package com.elevenhelevenm.practice.board.domain.pay.repository;

import com.elevenhelevenm.practice.board.domain.pay.model.PayV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PayRepositoryV2 extends JpaRepository<PayV2, Long> {

    @Query("SELECT p FROM PayV2 p WHERE p.successStatus = true")
    List<PayV2> findAllSuccess();
}
