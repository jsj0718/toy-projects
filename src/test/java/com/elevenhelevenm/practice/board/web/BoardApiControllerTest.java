package com.elevenhelevenm.practice.board.web;

import com.elevenhelevenm.practice.board.domain.board.Board;
import com.elevenhelevenm.practice.board.repository.BoardRepository;
import com.elevenhelevenm.practice.board.service.BoardService;
import com.elevenhelevenm.practice.board.web.dto.request.board.BoardSaveRequestDto;
import com.elevenhelevenm.practice.board.web.dto.request.board.BoardUpdateRequestDto;
import com.elevenhelevenm.practice.board.web.dto.response.board.BoardResponseDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoardApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    void cleanup() {
        boardRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("게시판에 글 등록 시 ID 값이 반환된다.")
    void saveBoard() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String author = "author";

        BoardSaveRequestDto requestDto = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        //when
        String url = "http://localhost:" + port + "/api/v1/board";

        MvcResult result = mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        Long savedId = Long.parseLong(result.getResponse().getContentAsString());

        //then
        Board board = boardRepository.findById(savedId).get();
        assertThat(board.getId()).isEqualTo(savedId);
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);
        assertThat(board.getAuthor()).isEqualTo(author);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("게시판 전체 조회 요청 시 페이징 처리가 되어 조회된다.")
    void selectBoards() throws Exception {
        //given
        String page = "0";

        //when
        String url = "http://localhost" + port + "/api/v1/board";

        MvcResult result = mvc.perform(get(url)
                        .param("page", page))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        json = json.substring(json.indexOf("["), json.indexOf("]") + 1);

        log.info("json : {}", json);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        //then
        List<BoardResponseDto> boards = Arrays.asList(objectMapper.readValue(json, BoardResponseDto[].class));
        assertThat(boards.size()).isLessThanOrEqualTo(10);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("게시판 ID로 조회 요청 시 해당 게시글 내용이 조회된다.")
    void selectBoard() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String author = "author";

        BoardSaveRequestDto requestDto = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        Long savedId = boardService.save(requestDto);

        //when
        String url = "http://localhost:" + port + "/api/v1/board/" + savedId;

        MvcResult result = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        BoardResponseDto board = objectMapper.readValue(json, BoardResponseDto.class);

        //then
        Board findBoard = boardRepository.findById(savedId).get();
        assertThat(board.getId()).isEqualTo(findBoard.getId());
        assertThat(board.getTitle()).isEqualTo(findBoard.getTitle());
        assertThat(board.getContent()).isEqualTo(findBoard.getContent());
        assertThat(board.getAuthor()).isEqualTo(findBoard.getAuthor());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("게시판 ID로 수정 요청 시 게시글 내용이 변경된다.")
    void updateBoard() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String author = "author";

        Long savedId = boardService.save(BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        String updatedTitle = "updatedTitle";
        String updatedContent = "updatedContent";

        BoardUpdateRequestDto requestDto = BoardUpdateRequestDto.builder()
                .title(updatedTitle)
                .content(updatedContent)
                .build();

        //when
        String url = "http://localhost:" + port + "/api/v1/board/" + savedId;

        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(savedId.toString()));

        //then
        Board board = boardRepository.findById(savedId).get();
        assertThat(board.getId()).isEqualTo(savedId);
        assertThat(board.getTitle()).isEqualTo(updatedTitle);
        assertThat(board.getContent()).isEqualTo(updatedContent);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("게시판 ID로 삭제 요청 시 게시글이 삭제된다.")
    void deleteBoard() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String author = "author";

        Long savedId = boardService.save(BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        Optional<Board> savedBoard = boardRepository.findById(savedId);
        assertThat(savedBoard).isNotEmpty();

        //when
        String url = "http://localhost:" + port + "/api/v1/board/" + savedId;

        mvc.perform(delete(url))
                .andExpect(status().isOk())
                .andExpect(content().string(savedId.toString()));

        //then
        Optional<Board> findBoard = boardRepository.findById(savedId);
        assertThat(findBoard).isEmpty();
    }
}