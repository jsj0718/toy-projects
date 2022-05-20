package com.elevenhelevenm.practice.board.domain.product.repository;

import com.elevenhelevenm.practice.board.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT MAX(p.id) FROM Product p WHERE p.createDate BETWEEN :startDate AND :endDate")
    Long findMaxId(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT Min(p.id) FROM Product p WHERE p.createDate BETWEEN :startDate AND :endDate")
    Long findMinId(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
