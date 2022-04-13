package com.elevenhelevenm.practice.board.web;

import com.elevenhelevenm.practice.board.config.security.dto.SessionUser;
import com.elevenhelevenm.practice.board.service.BoardService;
import com.elevenhelevenm.practice.board.service.UserService;
import com.elevenhelevenm.practice.board.web.dto.request.user.UserSaveRequestDto;
import com.elevenhelevenm.practice.board.web.dto.response.board.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserService userService;

    private final BoardService boardService;

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardResponseDto> boards = boardService.findAllDesc(pageable);
        model.addAttribute("boards", boards);

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login (
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            Model model,
            Authentication authentication) {

        if (authentication != null) {
            return "redirect:/";
        }

        if (error != null && exception != null) {
            model.addAttribute("error", error);
            model.addAttribute("exception", exception);
        }

        return "login";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProc(UserSaveRequestDto requestDto) {
        userService.join(requestDto);
        return "redirect:/login";
    }

    @GetMapping("/board/save")
    public String boardSavePage() {
        return "board-save";
    }

    @GetMapping("/board/update/{id}")
    public String boardUpdatePage(@PathVariable Long id, Model model) {
        BoardResponseDto responseDto = boardService.findById(id);
        model.addAttribute("board", responseDto);
        return "board-update";
    }
}