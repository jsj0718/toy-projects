package com.jsj0718.book.springboot.repository;

import com.jsj0718.book.springboot.domain.posts.Posts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest //H2 자동 실행
@Transactional
class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    void 게시글_저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String author = "테스트 작가";

        Posts posts = Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        postsRepository.save(posts);

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts findPosts = postsList.get(0);

        assertThat(findPosts.getTitle()).isEqualTo(title);
        assertThat(findPosts.getContent()).isEqualTo(content);
    }

    @Test
    void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 2, 25, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        log.info(">>>>> createDate = {}", posts.getCreatedDate());
        log.info(">>>>> modifiedDate = {}", posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}