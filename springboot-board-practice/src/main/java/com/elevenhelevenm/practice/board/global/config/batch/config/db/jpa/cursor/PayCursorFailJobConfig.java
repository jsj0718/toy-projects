package com.elevenhelevenm.practice.board.global.config.batch.config.db.jpa.cursor;

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
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class PayCursorFailJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 10;

    @Bean
    public Job payCursorFailJob() {
        return jobBuilderFactory.get("payCursorFailJob")
                .start(payCursorStep())
                .build();
    }

    @Bean
    @JobScope
    public Step payCursorStep() {
        return stepBuilderFactory.get("payCursorStep")
                .<PayV2, PayV2>chunk(chunkSize)
                .reader(payCursorReader())
                .processor(payCursorProcessor())
                .writer(payJpaCursorWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<PayV2> payCursorReader() {
        return new JpaCursorItemReaderBuilder<PayV2>()
                .name("payCursorReader")
                .queryString("SELECT p FROM PayV2 p WHERE p.successStatus = false")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<PayV2, PayV2> payCursorProcessor() {
        return item -> {
            item.success();
            return item;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<PayV2> payJpaCursorWriter() {
        JpaItemWriter<PayV2> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
