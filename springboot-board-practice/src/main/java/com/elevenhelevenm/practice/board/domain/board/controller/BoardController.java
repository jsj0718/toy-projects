package com.elevenhelevenm.practice.board.domain.board.controller;

import com.elevenhelevenm.practice.board.domain.board.dto.BoardDto;
import com.elevenhelevenm.practice.board.domain.board.service.BoardService;
import com.elevenhelevenm.practice.board.global.aop.PageLogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @PageLogTrace
    @GetMapping("/")
    public ModelAndView index(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              ModelAndView mav) {
        Page<BoardDto> boards = boardService.findAllDescV2(pageable);
        mav.addObject("boards", boards);

        mav.setViewName("board/home");

        return mav;
    }

    @PageLogTrace
    @GetMapping("/board/save")
    public void boardSavePage() {
    }

    @PageLogTrace
    @GetMapping("/board/select/{id}")
    public ModelAndView boardSelectPage(@PathVariable Long id,
                                        ModelAndView mav) {
        BoardDto responseDto = boardService.findByIdV2(id);
        mav.addObject("board", responseDto);

        mav.setViewName("board/select");

        return mav;
    }

    @PageLogTrace
    @GetMapping("/board/update/{id}")
    public ModelAndView boardUpdatePage(@PathVariable Long id,
                                        ModelAndView mav) {
        BoardDto responseDto = boardService.findByIdV2(id);
        mav.addObject("board", responseDto);

        mav.setViewName("board/update");

        return mav;
    }
}