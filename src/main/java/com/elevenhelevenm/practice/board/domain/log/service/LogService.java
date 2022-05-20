package com.elevenhelevenm.practice.board.domain.log.service;

import com.elevenhelevenm.practice.board.domain.log.dto.LogMapper;
import com.elevenhelevenm.practice.board.domain.log.dto.LogDto;
import com.elevenhelevenm.practice.board.domain.log.model.LogV2;
import com.elevenhelevenm.practice.board.domain.log.repository.LogRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LogService {

    private final LogRepositoryV2<LogV2> logRepository;

    @Transactional
    public Long saveLoginLog(LogDto logDto) {
        return logRepository.save(LogMapper.INSTANCE.toLoginLog(logDto)).getId();
    }

    @Transactional
    public Long savePageLog(LogDto logDto) {
        return logRepository.save(LogMapper.INSTANCE.toPageLog(logDto)).getId();
    }
}
