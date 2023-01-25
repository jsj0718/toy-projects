package com.elevenhelevenm.practice.board.web.controller.board;

import com.elevenhelevenm.practice.board.config.security.auth.LoginUser;
import com.elevenhelevenm.practice.board.config.security.dto.SessionUser;
import com.elevenhelevenm.practice.board.service.BoardService;
import com.elevenhelevenm.practice.board.web.dto.response.board.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        @LoginUser SessionUser user,
                        Model model) {

        Page<BoardResponseDto> boards = boardService.findAllDesc(pageable);
        model.addAttribute("boards", boards);

        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }

        return "board/index";
    }

    @GetMapping("/board/save")
    public String boardSavePage(@LoginUser SessionUser user,
                                Model model) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "board/board-save";
    }

    @GetMapping("/board/select/{id}")
    public String boardSelectPage(@PathVariable Long id,
                                  @LoginUser SessionUser user,
                                  Model model) {

        BoardResponseDto responseDto = boardService.findById(id);
        model.addAttribute("board", responseDto);

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "board/board-select";
    }

    @GetMapping("/board/update/{id}")
    public String boardUpdatePage(@PathVariable Long id,
                                  @LoginUser SessionUser user,
                                  Model model) {

        BoardResponseDto responseDto = boardService.findById(id);
        model.addAttribute("board", responseDto);

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "board/board-update";
    }

}