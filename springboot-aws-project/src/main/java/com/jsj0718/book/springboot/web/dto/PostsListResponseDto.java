package com.jsj0718.book.springboot.web.dto;

import com.jsj0718.book.springboot.domain.posts.Posts;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostsListResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.author = posts.getAuthor();
        this.modifiedDate = posts.getModifiedDate();
    }
}
