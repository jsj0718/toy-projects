package com.elevenhelevenm.practice.board.domain.product.repository;

import com.elevenhelevenm.practice.board.domain.product.model.ProductBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBackupRepository extends JpaRepository<ProductBackup, Long> {
}
