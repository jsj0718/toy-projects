package com.elevenhelevenm.practice.board.global.config.batch.config.flow;

import com.elevenhelevenm.practice.board.global.config.batch.config.TestBatchConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest(classes = {SplitFlowJobConfig.class, TestBatchConfig.class})
class SplitFlowJobConfigTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    @DisplayName("각 역할을 여러 step으로 나눠서 싱글 프로세스로 병렬화를 진행한다.")
    void parallelSteps() throws Exception {

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    }
}