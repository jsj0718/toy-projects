package com.elevenhelevenm.practice.board.domain.grid.controller;

import com.elevenhelevenm.practice.board.domain.grid.dto.GridDataDto;
import com.elevenhelevenm.practice.board.domain.grid.dto.GridDataMapper;
import com.elevenhelevenm.practice.board.domain.grid.repository.GridDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class GridController {

    private final GridDataRepository gridDataRepository;

    @GetMapping("/js/grid")
    public void grid(ModelAndView mav) {
    }

    @GetMapping("/gridData")
    @ResponseBody
    public List<GridDataDto> getGridData() {
        return gridDataRepository.findAll().stream().map(gridData -> GridDataMapper.INSTANCE.toDto(gridData)).collect(Collectors.toList());
    }
}
