package com.elevenhelevenm.practice.board.global.config.batch.config.basic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.batch.core.ExitStatus.*;
import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CustomJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    /**
     * 단일 Step 구성
     */
    @Bean
    public Job singleStepJob() {
        return jobBuilderFactory.get("singleStepJob")
                .start(step(null))
                .build();
    }


    @Bean
    @JobScope
    public Step step(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step!");
                    log.info("requestDate : {}", requestDate);
                    return FINISHED;
                }).build();
    }


    /**
     * 다중 Step 구성
     */
    @Bean
    public Job multipleStepJob() {
        return jobBuilderFactory.get("multipleStepJob")
                .start(startStep())
                .next(nextStep())
                .next(lastStep())
                .build();
    }

    @Bean
    public Step startStep() {
        return stepBuilderFactory.get("startStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Start Step!");
                    return FINISHED;
                }).build();
    }

    @Bean
    public Step nextStep() {
        return stepBuilderFactory.get("nextStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Next Step!");
                    return FINISHED;
                }).build();
    }

    @Bean
    public Step lastStep() {
        return stepBuilderFactory.get("lastStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Last Step!");
                    return FINISHED;
                }).build();
    }

    /**
     * Flow Job 구성
     */
    @Bean
    public Job flowJob() {
        return jobBuilderFactory.get("flowJob")
                .start(startStepV2())
                    .on("FAILED") //startStep의 ExitStatus가 FAILED라면
                    .to(failOverStep()) //FailOver Step 실행
                    .on("*") //FailOver 결과 상관없이
                    .to(writeStep()) //Write Step 실행
                    .on("*") //Write Step 상관없이
                    .end() //Flow 종료
                .from(startStepV2())
                    .on("COMPLETED")
                    .to(processStep())
                    .on("*")
                    .to(writeStep())
                    .on("*")
                    .end()
                .from(startStepV2())
                    .on("*")
                    .to(writeStep())
                    .on("*")
                    .end()
                .end()
                .build();
    }

    @Bean
    public Step startStepV2() {
        return stepBuilderFactory.get("startStepV2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Start Step!");

                    String result = "FAILED";

                    if (result.equals("COMPLETED")) contribution.setExitStatus(COMPLETED);
                    else if (result.equals("FAILED")) contribution.setExitStatus(FAILED);
                    else if (result.equals("UNKNOWN")) contribution.setExitStatus(UNKNOWN);

                    return FINISHED;
                }).build();
    }

    @Bean
    public Step failOverStep(){
        return stepBuilderFactory.get("failOverStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("FailOver Step!");
                    return FINISHED;
                })
                .build();
    }

    @Bean
    public Step processStep(){
        return stepBuilderFactory.get("processStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Process Step!");
                    return FINISHED;
                })
                .build();
    }


    @Bean
    public Step writeStep(){
        return stepBuilderFactory.get("writeStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Write Step!");
                    return FINISHED;
                })
                .build();
    }

}
