package com.elevenhelevenm.practice.board.global.config.batch.config.basic;

import com.elevenhelevenm.practice.board.domain.product.model.Product;
import com.elevenhelevenm.practice.board.domain.product.model.ProductBackup;
import com.elevenhelevenm.practice.board.domain.product.repository.ProductBackupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Configuration
public class SkipJobConfig {

    private static final String JOB_NAME = "skipBatch";
    private static final int CHUNK_SIZE = 100;
    private static final int SKIP_COUNT = 3;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private ProductBackupRepository productBackupRepository;

    @Autowired @Qualifier(JOB_NAME + "_reader")
    private JpaPagingItemReader<Product> reader;

    @Autowired @Qualifier(JOB_NAME + "_processor")
    private ItemProcessor<Product, ProductBackup> processor;

    @Bean(name = JOB_NAME)
    public Job job() {

        return jobBuilderFactory.get(JOB_NAME)
                .start(step1())
                .build();
    }

    @Bean(name = JOB_NAME + "_step")
    @JobScope
    public Step step1() {
        return stepBuilderFactory.get(JOB_NAME + "_step")
                .<Product, ProductBackup>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer())
                .faultTolerant()
                .skipLimit(SKIP_COUNT)
                .skip(TimeoutException.class)
                .noSkip(SQLException.class)
                .build();
    }

    @Bean(name = JOB_NAME + "_reader")
    @StepScope
    public JpaPagingItemReader<Product> reader() {
        log.info(">>>>>> itemReader");
        return new JpaPagingItemReaderBuilder<Product>()
                .name(JOB_NAME + "_reader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("SELECT p FROM Product p")
                .build();
    }

    @Bean(name = JOB_NAME + "_processor")
    @StepScope
    public ItemProcessor<Product, ProductBackup> processor() {
        log.info(">>>>>> itemProcessor");
        return ProductBackup::new;
    }

    @Bean(name = JOB_NAME + "_writer")
    @StepScope
    public ItemWriter<ProductBackup> writer() {
        log.info(">>>>>> itemWriter");
        return items -> productBackupRepository.saveAll(items);
    }
}
