package com.elevenhelevenm.practice.board.service;

import com.elevenhelevenm.practice.board.domain.board.Board;
import com.elevenhelevenm.practice.board.exception.CustomException;
import com.elevenhelevenm.practice.board.exception.errorcode.BoardErrorCode;
import com.elevenhelevenm.practice.board.repository.BoardRepository;
import com.elevenhelevenm.practice.board.web.dto.request.board.BoardSaveRequestDto;
import com.elevenhelevenm.practice.board.web.dto.request.board.BoardUpdateRequestDto;
import com.elevenhelevenm.practice.board.web.dto.response.board.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<BoardResponseDto> findAllDesc(@PageableDefault(size = 10) Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());

        return boardRepository.findAll(pageable)
                .map(BoardResponseDto::new);
    }

    public BoardResponseDto findById(Long id) {
        log.debug("boardService findById Exception -> 존재하지 않는 게시글 : {}", id);
        return new BoardResponseDto(boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BoardErrorCode.NotFoundBoardId)));
    }

    @Transactional
    public Long save(BoardSaveRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, BoardUpdateRequestDto requestDto) {
        log.debug("boardService update Exception -> 존재하지 않는 게시글 : {}", id);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BoardErrorCode.NotFoundBoardId));

        board.update(requestDto.getTitle(), requestDto.getContent());

        return board.getId();
    }

    @Transactional
    public void delete(Long id) {
        log.debug("boardService delete Exception -> 존재하지 않는 게시글 : {}", id);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BoardErrorCode.NotFoundBoardId));

        boardRepository.delete(board);
    }
}
