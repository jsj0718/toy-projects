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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class PayPagingFailJobConfigV2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 10;

    @Bean
    public Job payPagingFailJobV2() {
        return jobBuilderFactory.get("payPagingFailJobV2")
                .start(payPagingStepV2())
                .build();
    }

    @Bean
    @JobScope
    public Step payPagingStepV2() {
        return stepBuilderFactory.get("payPagingStepV2")
                .<PayV2, PayV2>chunk(chunkSize)
                .reader(payPagingReaderV2())
                .processor(payPagingProcessorV2())
                .writer(payJpaPagingWriterV2())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<PayV2> payPagingReaderV2() {
        JpaPagingItemReader<PayV2> reader = new JpaPagingItemReader<>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString("SELECT p FROM PayV2 p WHERE p.successStatus = false");
        reader.setPageSize(chunkSize);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setName("payPagingReaderV2");

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<PayV2, PayV2> payPagingProcessorV2() {
        return item -> {
            item.success();
            return item;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<PayV2> payJpaPagingWriterV2() {
        JpaItemWriter<PayV2> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
