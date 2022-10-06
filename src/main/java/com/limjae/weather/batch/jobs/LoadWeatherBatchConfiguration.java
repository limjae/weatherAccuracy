package com.limjae.weather.batch.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// read datas from openapi
@Slf4j
@Configuration
@RequiredArgsConstructor
public class LoadWeatherBatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job loadWeatherJob() {
        return jobBuilderFactory.get("stepNextJob")
                .start(shortForecastLoadStep())
                .next(longForecastLoadStep())
                .build();
    }

    // 지역별 분리 필요
    @Bean
    public Step shortForecastLoadStep() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // 지역별 분리 필요
    @Bean
    public Step longForecastLoadStep() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
