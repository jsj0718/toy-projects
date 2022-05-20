package com.elevenhelevenm.practice.board.global.config.batch.config.basic;

import com.elevenhelevenm.practice.board.domain.product.model.Product;
import com.elevenhelevenm.practice.board.domain.product.model.ProductBackup;
import com.elevenhelevenm.practice.board.global.config.batch.config.TestBatchConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@EntityScan("com.elevenhelevenm.practice.board.domain.product.model")
@EnableJpaRepositories("com.elevenhelevenm.practice.board.domain.product.repository")
@SpringBatchTest
@SpringBootTest(classes = {SkipJobConfig.class, TestBatchConfig.class})
class SkipJobConfigTest {
    static final String JOB_NAME = "skipBatch";

    @MockBean(name = JOB_NAME + "_reader")
    JpaPagingItemReader<Product> reader;

    @MockBean(name = JOB_NAME +"_processor")
    ItemProcessor<Product, ProductBackup> processor;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    @DisplayName("TimeoutException에 대해서 스킵한다.")
    void skipToTimeoutException() throws Exception {
        //given
        given(reader.read())
                .willReturn(new Product("product1", 10000L, null), new Product("product1", 10000L, null), null);

        given(processor.process(any()))
                .willThrow(TimeoutException.class)
                .willReturn(null);

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep(JOB_NAME + "_step");

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    @DisplayName("SQLException에 대해서 재시도를 진행하지 않는다.")
    void noRetryByConnectionTimeout() throws Exception {
        //given
        given(reader.read())
                .willReturn(new Product("product1", 10000L, null), new Product("product1", 10000L, null), null);

        given(processor.process(any()))
                .willThrow(SQLException.class)
                .willReturn(null);

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep(JOB_NAME + "_step");

        //then
        verify(processor, times(1)).process(any());
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.FAILED);
    }
}