package com.elevenhelevenm.practice.board.domain.board;

import com.elevenhelevenm.practice.board.config.security.SecurityConfig;
import com.elevenhelevenm.practice.board.service.BoardService;
import com.elevenhelevenm.practice.board.web.BoardApiController;
import com.elevenhelevenm.practice.board.web.dto.request.BoardSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BoardApiController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
class BoardTest {

    @MockBean
    BoardService boardService;

    @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("게시판에 글을 등록하면 ID 값이 반환된다.")
    void saveBoard() throws Exception {
        String title = "title";
        String content = "content";
        String author = "author";

        BoardSaveRequestDto requestDto = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        given(boardService.save(requestDto)).willReturn(1L);

        mvc.perform(post("/api/v1/board")
                .param("title", title)
                .param("content", content)
                .param("author", author))
                .andExpect(status().isOk());
    }
}