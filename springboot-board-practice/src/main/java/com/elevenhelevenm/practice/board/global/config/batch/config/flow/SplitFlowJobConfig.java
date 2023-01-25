package com.elevenhelevenm.practice.board.global.config.batch.config.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SplitFlowJobConfig {

    private final static String JOB_NAME = "splitFlowJobBatch";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(name = JOB_NAME + "taskPool")
    public TaskExecutor executor() {
        return new SimpleAsyncTaskExecutor("multi-thread-");
    }

    @Bean(name = JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(splitFlow())
                .next(step4())
                .build()
                .build();
    }

    @Bean(name = JOB_NAME + "_splitFlow")
    public Flow splitFlow() {
        return new FlowBuilder<SimpleFlow>(JOB_NAME + "_splitFlow")
                .split(executor())
                .add(flow1(), flow2())
                .build();
    }

    @Bean(name = JOB_NAME + "flow1")
    public Flow flow1() {
        return new FlowBuilder<SimpleFlow>(JOB_NAME + "flow1")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean(name = JOB_NAME + "flow2")
    public Flow flow2() {
        return new FlowBuilder<SimpleFlow>(JOB_NAME + "flow2")
                .start(step3())
                .build();
    }

    @Bean(name = JOB_NAME + "_step1")
    public Step step1() {
        return stepBuilderFactory.get(JOB_NAME + "_step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("this is step1");
                    return FINISHED;
                })
                .build();
    }

    @Bean(name = JOB_NAME + "_step2")
    public Step step2() {
        return stepBuilderFactory.get(JOB_NAME + "_step2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("this is step2");
                    return FINISHED;
                })
                .build();
    }

    @Bean(name = JOB_NAME + "_step3")
    public Step step3() {
        return stepBuilderFactory.get(JOB_NAME + "_step3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("this is step3");
                    return FINISHED;
                })
                .build();
    }

    @Bean(name = JOB_NAME + "_step4")
    public Step step4() {
        return stepBuilderFactory.get(JOB_NAME + "_step4")
                .tasklet((contribution, chunkContext) -> {
                    log.info("this is step4");
                    return FINISHED;
                })
                .build();
    }

}
