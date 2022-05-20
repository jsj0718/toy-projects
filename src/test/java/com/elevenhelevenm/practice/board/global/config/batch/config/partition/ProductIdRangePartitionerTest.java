package com.elevenhelevenm.practice.board.global.config.batch.config.partition;

import com.elevenhelevenm.practice.board.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.ExecutionContext;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductIdRangePartitionerTest {

    private static ProductIdRangePartitioner partitioner;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("ID가 gridSize에 맞게 분할된다.")
    void partitionByGridSize() {
        //given
        Mockito.lenient()
                .when(productRepository.findMinId(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(0L);

        Mockito.lenient()
                .when(productRepository.findMaxId(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(9L);

        partitioner = new ProductIdRangePartitioner(productRepository, LocalDate.of(2022, 05, 10), LocalDate.of(2022, 05, 10));

        //when
        Map<String, ExecutionContext> executionContextMap = partitioner.partition(5);

        //then
        ExecutionContext partition0 = executionContextMap.get("partition0");
        assertThat(partition0.getLong("minId")).isEqualTo(0L);
        assertThat(partition0.getLong("maxId")).isEqualTo(1L);

        ExecutionContext partition4 = executionContextMap.get("partition4");
        assertThat(partition4.getLong("minId")).isEqualTo(8L);
        assertThat(partition4.getLong("maxId")).isEqualTo(9L);
    }

}