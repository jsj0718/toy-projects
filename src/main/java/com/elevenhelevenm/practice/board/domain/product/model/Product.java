package com.elevenhelevenm.practice.board.domain.product.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long price;
    private LocalDate createDate;

    @Builder
    public Product(String name, Long price, LocalDate createDate) {
        this.name = name;
        this.price = price;
        this.createDate = createDate;
    }
}
