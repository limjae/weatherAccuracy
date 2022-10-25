package com.limjae.weather.batch.job;

import com.limjae.weather.batch.tasklet.LoadAfter1DayWithShortApiTasklet;
import com.limjae.weather.batch.tasklet.LoadAfter2DayWithShortApiTasklet;
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
    private final LoadAfter1DayWithShortApiTasklet loadAfter1DayWithShortApiTasklet;
    private final LoadAfter2DayWithShortApiTasklet loadAfter2DayWithShortApiTasklet;

    @Bean
    public Job liveApiJob() {
        return jobBuilderFactory.get("LiveApiLoadBatch")
                .start(liveApiStep())
                .build();
    }

    @Bean
    public Job shortApiJob() {
        return jobBuilderFactory.get("ShortApiLoadBatch")
                .start(day1ShortApiStep())
                .next(day2ShortApiStep())
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
    @JobScope
    public Step day1ShortApiStep() {
        return stepBuilderFactory.get("ShortApiStep")
                .tasklet(loadAfter1DayWithShortApiTasklet)
                .build();
    }

    @Bean
    @JobScope
    public Step day2ShortApiStep() {
        return stepBuilderFactory.get("ShortApiStep")
                .tasklet(loadAfter2DayWithShortApiTasklet)
                .build();
    }

}
