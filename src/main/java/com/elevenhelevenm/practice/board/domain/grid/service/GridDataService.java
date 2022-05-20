package com.elevenhelevenm.practice.board.domain.grid.service;

import com.elevenhelevenm.practice.board.domain.grid.dto.GridDataDto;
import com.elevenhelevenm.practice.board.domain.grid.dto.GridDataMapper;
import com.elevenhelevenm.practice.board.domain.grid.repository.GridDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GridDataService {

    private final GridDataRepository gridDataRepository;

    @Transactional
    public Long save(GridDataDto gridDataDto) {
        return gridDataRepository.save(GridDataMapper.INSTANCE.toEntity(gridDataDto)).getId();
    }
}
