package com.elevenhelevenm.practice.board.global.config.batch.config.partition;

import com.elevenhelevenm.practice.board.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ProductIdRangePartitioner implements Partitioner {

    private final ProductRepository productRepository;
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * gridSize : 몇 개의 StepExecution을 생성할지 설정하는 값
     */    
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Long max = productRepository.findMaxId(startDate, endDate);
        Long min = productRepository.findMinId(startDate, endDate);
        Long targetSize = (max - min) / gridSize + 1;

        Map<String, ExecutionContext> result = new HashMap<>();
        Long number = 0L; //partition 번호
        Long start = min;
        Long end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= max) end = max;

            value.put("minId", start);
            value.put("maxId", end);
            start += targetSize;
            end += targetSize;
            number++;
        }

        return result;
    }
}
