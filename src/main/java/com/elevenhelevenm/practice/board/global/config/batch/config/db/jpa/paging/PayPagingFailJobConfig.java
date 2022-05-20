package com.elevenhelevenm.practice.board.global.config.batch.config.db.jpa.paging;

import com.elevenhelevenm.practice.board.domain.pay.model.PayV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class PayPagingFailJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 10;

    @Bean
    public Job payPagingFailJob() {
        return jobBuilderFactory.get("payPagingFailJob")
                .start(payPagingStep())
                .build();
    }

    @Bean
    @JobScope
    public Step payPagingStep() {
        return stepBuilderFactory.get("payPagingStep")
                .<PayV2, PayV2>chunk(chunkSize)
                .reader(payPagingReader())
                .processor(payPagingProcessor())
                .writer(payJpaPagingWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<PayV2> payPagingReader() {
        return new JpaPagingItemReaderBuilder<PayV2>()
                .queryString("SELECT p FROM PayV2 p WHERE p.successStatus = false")
                .pageSize(chunkSize)
                .entityManagerFactory(entityManagerFactory)
                .name("payPagingReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<PayV2, PayV2> payPagingProcessor() {
        return item -> {
            item.success();
            return item;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<PayV2> payJpaPagingWriter() {
        JpaItemWriter<PayV2> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
