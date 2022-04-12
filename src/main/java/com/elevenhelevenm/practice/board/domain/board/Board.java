package com.elevenhelevenm.practice.board.domain.board;

import com.elevenhelevenm.practice.board.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Builder
@Entity
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String author;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
