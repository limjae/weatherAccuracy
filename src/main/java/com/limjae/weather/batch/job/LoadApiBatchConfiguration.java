package com.limjae.weather.batch.job;

import com.limjae.weather.batch.tasklet.LoadLiveApiTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// read datas from openapi
@Slf4j
@Configuration
@RequiredArgsConstructor
public class LoadApiBatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final LoadLiveApiTasklet loadLiveApiTasklet;

    @Bean
    public Job liveApiJob() {
        return jobBuilderFactory.get("OpenApiLoadBatchEvery6")
                .start(liveApiStep())
                .build();
    }

    @Bean
    @JobScope
    public Step liveApiStep() {
        return stepBuilderFactory.get("liveApiStep")
                .tasklet(loadLiveApiTasklet)
                .build();
    }

    @Bean
    public Step midtermApiStep() {
        return stepBuilderFactory.get("loadMidtermApiStep")
                .tasklet(loadLiveApiTasklet)
                .build();
    }
}
