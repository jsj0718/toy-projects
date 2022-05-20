package com.elevenhelevenm.practice.board.global.config.batch.config.db.jpa.paging;

import com.elevenhelevenm.practice.board.domain.pay.model.PayV2;
import com.elevenhelevenm.practice.board.domain.pay.repository.PayRepositoryV2;
import com.elevenhelevenm.practice.board.global.config.batch.config.TestBatchConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.assertj.core.api.Assertions.assertThat;

@EntityScan("com.elevenhelevenm.practice.board.domain.pay.model")
@EnableJpaRepositories("com.elevenhelevenm.practice.board.domain.pay.repository")
@EnableTransactionManagement
@SpringBatchTest
@SpringBootTest(classes = {PayPagingFailJobConfigV2.class, TestBatchConfig.class})
class PayPagingFailJobConfigV2Test {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    PayRepositoryV2 payRepository;

    @BeforeEach
    void clearJobExecutions() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @AfterEach
    void tearDown() throws Exception {
        payRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("같은 조건을 읽고 업데이트 할 때 - 해결 예시 2")
    void batchPagingTestV2() throws Exception {
        //given
        for (long i = 0; i < 50; i++) {
            payRepository.save(new PayV2(i, false));
        }

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(payRepository.findAllSuccess().size()).isEqualTo(50);
    }

}