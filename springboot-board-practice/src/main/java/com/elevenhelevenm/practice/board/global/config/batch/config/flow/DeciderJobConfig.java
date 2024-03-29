package com.elevenhelevenm.practice.board.global.config.batch.config.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DeciderJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job deciderJob() {
        return jobBuilderFactory.get("deciderJob")
                .start(deciderStartStep())
                .next(decider())
                .from(decider())
                .on("ODD")
                .to(oddStep())
                .from(decider())
                .on("EVEN")
                .to(evenStep())
                .end()
                .build();
    }

    @Bean
    public Step deciderStartStep() {
        return stepBuilderFactory.get("deciderStartStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> Decider Start Step!");
                    return FINISHED;
                })
                .build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> Even Step!");
                    return FINISHED;
                })
                .build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> Odd Step!");
                    return FINISHED;
                })
                .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new OddDecider();
    }

    private static class OddDecider implements JobExecutionDecider {
        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            Random random = new Random();

            int randomNumber = random.nextInt(50) + 1;
            log.info(">>>>> 랜덤 숫자 : {}", randomNumber);

            if (randomNumber % 2 == 0) return new FlowExecutionStatus("EVEN");
            else return new FlowExecutionStatus("ODD");
        }
    }
}