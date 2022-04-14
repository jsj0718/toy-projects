package com.elevenhelevenm.practice.board.web;

import com.elevenhelevenm.practice.board.config.security.auth.LoginUser;
import com.elevenhelevenm.practice.board.config.security.dto.SessionUser;
import com.elevenhelevenm.practice.board.domain.board.Board;
import com.elevenhelevenm.practice.board.repository.BoardRepository;
import com.elevenhelevenm.practice.board.service.BoardService;
import com.elevenhelevenm.practice.board.web.dto.response.board.BoardResponseDto;
import com.elevenhelevenm.practice.board.web.dto.request.board.BoardSaveRequestDto;
import com.elevenhelevenm.practice.board.web.dto.request.board.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardRepository boardRepository;

    private final BoardService boardService;

    @GetMapping("/api/v1/board")
    public Page<BoardResponseDto> boardDtoList(@PageableDefault(size = 10) Pageable pageable) {
        return boardRepository.findAll(pageable)
                .map(BoardResponseDto::new);
    }

    @GetMapping("/api/v1/board/{id}")
    public BoardResponseDto boardDto(@PathVariable Long id) {
        return new BoardResponseDto(boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 게시글입니다.")));
    }

    @PostMapping("/api/v1/board")
    public Long save(@RequestBody BoardSaveRequestDto requestDto) {
        return boardService.save(requestDto);
    }

    @PutMapping("/api/v1/board/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/board/{id}")
    public Long delete(@PathVariable Long id) {
        boardService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/board-pagination")
    public Page<BoardResponseDto> index(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardResponseDto> boards = boardService.findAllDesc(pageable);

        return boards;
    }

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 100; i++) {
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .author("author" + i)
                    .build();

            boardRepository.save(board);
        }
    }
}
