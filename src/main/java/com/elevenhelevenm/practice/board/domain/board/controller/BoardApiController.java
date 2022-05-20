package com.elevenhelevenm.practice.board.domain.board.controller;

import com.elevenhelevenm.practice.board.domain.board.dto.BoardDto;
import com.elevenhelevenm.practice.board.domain.board.dto.request.BoardSaveRequestDto;
import com.elevenhelevenm.practice.board.domain.board.dto.request.BoardUpdateRequestDto;
import com.elevenhelevenm.practice.board.domain.board.dto.response.BoardResponseDto;
import com.elevenhelevenm.practice.board.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BoardApiController {

    private final BoardService boardService;

    //API V1
    @GetMapping("/v1/board")
    public Page<BoardResponseDto> boardDtoList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardService.findAllDesc(pageable);
    }

    @GetMapping("/v1/board/{id}")
    public BoardResponseDto boardDto(@PathVariable Long id) {
        return boardService.findById(id);
    }

    @PostMapping("/v1/board")
    public Long saveBoard(@Valid @RequestBody BoardSaveRequestDto requestDto) {
        return boardService.save(requestDto);
    }

    @PutMapping("/v1/board/{id}")
    public Long updateBoard(@PathVariable Long id, @Valid @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    @DeleteMapping("/v1/board/{id}")
    public Long deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return id;
    }

    //API V2
    @GetMapping("/v2/board")
    public Page<BoardDto> boardDtoListV2(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardService.findAllDescV2(pageable);
    }

    @GetMapping("/v2/board/{id}")
    public BoardDto boardDtoV2(@PathVariable Long id) {
        return boardService.findByIdV2(id);
    }

    @PostMapping("/v2/board")
    public Long saveBoardV2(@Valid @RequestBody BoardDto requestDto) {
        return boardService.saveV2(requestDto);
    }

    @PutMapping("/v2/board/{id}")
    public Long updateBoardV2(@PathVariable Long id, @Valid @RequestBody BoardDto requestDto) {
        return boardService.updateV2(id, requestDto);
    }

}
