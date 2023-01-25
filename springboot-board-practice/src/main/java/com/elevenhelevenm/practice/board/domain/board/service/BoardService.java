package com.elevenhelevenm.practice.board.domain.board.service;

import com.elevenhelevenm.practice.board.domain.board.dto.BoardDto;
import com.elevenhelevenm.practice.board.domain.board.dto.BoardMapper;
import com.elevenhelevenm.practice.board.domain.board.dto.request.BoardSaveRequestDto;
import com.elevenhelevenm.practice.board.domain.board.dto.request.BoardUpdateRequestDto;
import com.elevenhelevenm.practice.board.domain.board.dto.response.BoardResponseDto;
import com.elevenhelevenm.practice.board.domain.board.model.Board;
import com.elevenhelevenm.practice.board.domain.board.repository.BoardRepository;
import com.elevenhelevenm.practice.board.domain.user.model.Role;
import com.elevenhelevenm.practice.board.global.errors.code.BoardErrorCode;
import com.elevenhelevenm.practice.board.global.errors.code.UserErrorCode;
import com.elevenhelevenm.practice.board.global.errors.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    //V1
    public Page<BoardResponseDto> findAllDesc(@PageableDefault(size = 10) Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());

        return boardRepository.findAll(pageable)
                .map(BoardResponseDto::new);
    }

    public BoardResponseDto findById(Long id) {
        return new BoardResponseDto(boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BoardErrorCode.NotFoundBoardId)));
    }

    @Transactional
    public Long save(BoardSaveRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BoardErrorCode.NotFoundBoardId));

        board.update(requestDto.getTitle(), requestDto.getContent());

        return board.getId();
    }

    @Transactional
    public void delete(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BoardErrorCode.NotFoundBoardId));

        if (!isLogin()) {
            throw new CustomException(UserErrorCode.InvalidAuthentication);
        }

        if (!getUsername().equals(board.getAuthor()) && getRole().equals(Role.MEMBER.getKey())) {
            throw new CustomException(UserErrorCode.NoAuthorization);
        }

        boardRepository.delete(board);
    }


    //V2
    public Page<BoardDto> findAllDescV2(@PageableDefault(size = 10) Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());

        return boardRepository.findAll(pageable)
                .map(board -> BoardMapper.INSTANCE.toDto(board));
    }

    public BoardDto findByIdV2(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BoardErrorCode.NotFoundBoardId));

        return BoardMapper.INSTANCE.toDto(board);
    }

    @Transactional
    public Long saveV2(BoardDto requestDto) {
        return boardRepository.save(BoardMapper.INSTANCE.toEntity(requestDto)).getId();
    }

    @Transactional
    public Long updateV2(Long id, BoardDto requestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BoardErrorCode.NotFoundBoardId));

        if (!isLogin()) {
            throw new CustomException(UserErrorCode.InvalidAuthentication);
        }

        if (!getUsername().equals(board.getAuthor()) && getRole().equals(Role.MEMBER.getKey())) {
            throw new CustomException(UserErrorCode.NoAuthorization);
        }

        BoardMapper.INSTANCE.updateBoardFromDto(requestDto, board);

        return id;
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private String getRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
    }

    private boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return true;
    }
}
