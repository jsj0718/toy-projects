package com.elevenhelevenm.practice.board.global.config.batch.config.partition;

import com.elevenhelevenm.practice.board.domain.product.model.Product;
import com.elevenhelevenm.practice.board.domain.product.model.ProductBackup;
import com.elevenhelevenm.practice.board.domain.product.repository.ProductBackupRepository;
import com.elevenhelevenm.practice.board.domain.product.repository.ProductRepository;
import com.elevenhelevenm.practice.board.global.config.batch.config.TestBatchConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@EntityScan("com.elevenhelevenm.practice.board.domain.product.model")
@EnableJpaRepositories("com.elevenhelevenm.practice.board.domain.product.repository")
@EnableTransactionManagement
@SpringBatchTest
@SpringBootTest(classes = {PartitionLocalConfig.class, TestBatchConfig.class})
@TestPropertySource(properties = "chunkSize=5")
class PartitionLocalConfigTest {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductBackupRepository productBackupRepository;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @AfterEach
    void after() {
        productRepository.deleteAll();
        productBackupRepository.deleteAll();
    }

    @Test
    @DisplayName("Product가 ProductBackup으로 이관된다.")
    void productToProductBackup() throws Exception {
        //given
        Long price = 1000L;
        LocalDate txDate = LocalDate.of(2022, 05, 11);

        List<Product> products = new ArrayList<>();
        int expectedCount = 50;
        for (int i = 1; i <= expectedCount; i++) {
            products.add(new Product("product" + i, price * i, txDate));
        }
        productRepository.saveAll(products);

        JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParametersBuilder()
                .addString("startDate", txDate.format(FORMATTER))
                .addString("endDate", txDate.format(FORMATTER))
                .toJobParameters();

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        List<ProductBackup> backups = productBackupRepository.findAll();
        assertThat(backups).hasSize(expectedCount);

        List<Map<String, Object>> metaTable = jdbcTemplate.queryForList("SELECT step_name, status, commit_count, read_count, write_count FROM BATCH_STEP_EXECUTION");

        for (Map<String, Object> step : metaTable) {
            log.info("meta table row = {}", step);
        }
    }
}