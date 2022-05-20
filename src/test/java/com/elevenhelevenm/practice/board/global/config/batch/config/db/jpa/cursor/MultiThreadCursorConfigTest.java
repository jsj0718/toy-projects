package com.elevenhelevenm.practice.board.global.config.batch.config.db.jpa.cursor;

import com.elevenhelevenm.practice.board.domain.product.model.Product;
import com.elevenhelevenm.practice.board.domain.product.model.ProductBackup;
import com.elevenhelevenm.practice.board.domain.product.repository.ProductBackupRepository;
import com.elevenhelevenm.practice.board.domain.product.repository.ProductRepository;
import com.elevenhelevenm.practice.board.global.config.batch.config.TestBatchConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@EntityScan("com.elevenhelevenm.practice.board.domain.product.model")
@EnableJpaRepositories("com.elevenhelevenm.practice.board.domain.product.repository")
@EnableTransactionManagement
@SpringBatchTest
@SpringBootTest(classes = {MultiThreadCursorConfig.class, TestBatchConfig.class})
@TestPropertySource(properties = {"chunkSize=1", "poolSize=5"})
class MultiThreadCursorConfigTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductBackupRepository productBackupRepository;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @AfterEach
    void after() {
        productRepository.deleteAll();
        productBackupRepository.deleteAll();
    }

    @Test
    @DisplayName("Cursor 분산처리 테스트")
    void cursorByParallel() throws Exception {
        //given
        LocalDate createDate = LocalDate.of(2022, 05, 10);
        Long price = 1000L;

        for (int i=0; i<10; i++) {
            productRepository.save(Product.builder()
                    .name("product" + i)
                    .price(i * price)
                    .createDate(createDate)
                    .build());
        }

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("createDate", createDate.toString())
                .toJobParameters();

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        List<ProductBackup> backups = productBackupRepository.findAll();
        backups.sort(Comparator.comparing(ProductBackup::getPrice));

        assertThat(backups).hasSize(10);
        assertThat(backups.get(0).getPrice()).isEqualTo(0L);
        assertThat(backups.get(9).getPrice()).isEqualTo(9000L);
    }
}