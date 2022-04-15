package com.elevenhelevenm.practice.board.web.controller.board;

import com.elevenhelevenm.practice.board.service.BoardService;
import com.elevenhelevenm.practice.board.web.dto.request.board.BoardSaveRequestDto;
import com.elevenhelevenm.practice.board.web.dto.request.board.BoardUpdateRequestDto;
import com.elevenhelevenm.practice.board.web.dto.response.board.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/api/v1/board")
    public Page<BoardResponseDto> boardDtoList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardService.findAllDesc(pageable);
    }

    @GetMapping("/api/v1/board/{id}")
    public BoardResponseDto boardDto(@PathVariable Long id) {
        return boardService.findById(id);
    }

    @PostMapping("/api/v1/board")
    public Long saveBoard(@Valid @RequestBody BoardSaveRequestDto requestDto) {
        return boardService.save(requestDto);
    }

    @PutMapping("/api/v1/board/{id}")
    public Long updateBoard(@PathVariable Long id, @Valid @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/board/{id}")
    public Long deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return id;
    }

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 100; i++) {
            BoardSaveRequestDto requestDto = BoardSaveRequestDto.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .author("author" + i)
                    .build();

            boardService.save(requestDto);
        }
    }
}
