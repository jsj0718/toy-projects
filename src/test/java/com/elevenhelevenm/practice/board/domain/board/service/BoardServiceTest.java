package com.elevenhelevenm.practice.board.domain.board.service;

import com.elevenhelevenm.practice.board.domain.board.dto.request.BoardUpdateRequestDto;
import com.elevenhelevenm.practice.board.domain.board.repository.BoardRepository;
import com.elevenhelevenm.practice.board.global.errors.code.BoardErrorCode;
import com.elevenhelevenm.practice.board.global.errors.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@Slf4j
@ExtendWith(MockitoExtension.class) //Mockito 사용 시 추가
class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;

    @InjectMocks
    BoardService boardService;

    /**
     * Command Test
     * private method의 Stubbing 방법 찾아보기
     */
/*
    @Test
    @DisplayName("ID 값과 Title, Content 값으로 수정 요청 시 해당 게시글이 변경된다.")
    void updateBoard() {
        //given
        Long id = 1L;
        String title = "title";
        String content = "content";
        String author = "author";

        Optional<Board> board = Optional.of(Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        given(boardRepository.findById(id)).willReturn(board);

        Optional<Board> saveBoard = boardRepository.findById(id);
        assertThat(saveBoard.get().getTitle()).isEqualTo(title);
        assertThat(saveBoard.get().getContent()).isEqualTo(content);
        assertThat(saveBoard.get().getAuthor()).isEqualTo(author);

        //when
        String updatedTitle = "updatedTitle";
        String updatedContent = "updatedContent";
        BoardDto requestDto = BoardDto.builder()
                .title(updatedTitle)
                .content(updatedContent)
                .build();

        boardService.updateV2(id, requestDto);

        //then
        Optional<Board> findBoard = boardRepository.findById(id);
        assertThat(findBoard.get().getTitle()).isEqualTo(updatedTitle);
        assertThat(findBoard.get().getContent()).isEqualTo(updatedContent);
        assertThat(findBoard.get().getAuthor()).isEqualTo(author);
    }

    @Test
    @DisplayName("ID 값이 주어지면 해당 게시글을 삭제한다.")
    void deleteBoard() {
        //given
        Long id = 1L;
        String title = "title";
        String content = "content";
        String author = "author";

        Optional<Board> board = Optional.of(Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        given(boardRepository.findById(id)).willReturn(board);

        //when
        doAnswer(invocation -> given(boardRepository.findById(id)).willReturn(Optional.empty()))
                .when(boardRepository).delete(board.get());

        //then
        Optional<Board> saveBoard = boardRepository.findById(id);
        assertThat(saveBoard).isNotEmpty();

        boardService.delete(id);

        Optional<Board> findBoard = boardRepository.findById(id);
        assertThat(findBoard).isEmpty();
    }
*/

    @Test
    @DisplayName("수정 시 게시글이 없으면 오류가 발생한다.")
    void updateException() {
        //given
        Long id = 1L;
        String title = "title";
        String content = "content";

        BoardUpdateRequestDto requestDto = BoardUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        given(boardRepository.findById(id)).willReturn(Optional.empty());

        //when
        CustomException e = assertThrows(CustomException.class, () -> boardService.update(id, requestDto));

        //then
        assertThat(e.getErrorCode()).isEqualTo(BoardErrorCode.NotFoundBoardId);
    }

    @Test
    @DisplayName("삭제 시 게시글이 없으면 오류가 발생한다.")
    void deleteException() {
        //given
        Long id = 1L;

        given(boardRepository.findById(id)).willReturn(Optional.empty());

        //when
        CustomException e = assertThrows(CustomException.class, () -> boardService.delete(id));

        //then
        assertThat(e.getErrorCode()).isEqualTo(BoardErrorCode.NotFoundBoardId);
    }

    /**
     * Query Test
     */
    @Test
    @DisplayName("조회 시 게시글이 없으면 오류가 발생한다.")
    void findByIdException() {
        //given
        Long id = 1L;

        given(boardRepository.findById(id)).willReturn(Optional.empty());

        //when
        CustomException e = assertThrows(CustomException.class, () -> boardService.findById(id));

        //then
        assertThat(e.getErrorCode()).isEqualTo(BoardErrorCode.NotFoundBoardId);
    }

}