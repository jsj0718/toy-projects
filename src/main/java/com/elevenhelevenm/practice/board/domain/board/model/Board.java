package com.elevenhelevenm.practice.board.domain.board.model;

import com.elevenhelevenm.practice.board.domain.common.model.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter @Setter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

