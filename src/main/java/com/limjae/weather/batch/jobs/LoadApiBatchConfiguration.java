package com.limjae.weather.batch.jobs;

import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

// read datas from openapi
@Slf4j
@Configuration
@RequiredArgsConstructor
public class LoadApiBatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WeatherService weatherService;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("stepNextJob")
                .start(loadLiveApiStep())
                .next(loadMidtermApiStep())
                .build();
    }

    // 지역별 분리 필요
    @Bean
    public Step loadLiveApiStep() {
        return stepBuilderFactory.get("loadLiveApiStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> Loading From Live Forecast Api");
                    weatherService.loadToDB(OpenApiType.LIVE);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step loadMidtermApiStep() {
        return stepBuilderFactory.get("loadMidtermApiStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> Loading From Midterm Forecast Api");
                    weatherService.loadToDB(OpenApiType.LIVE);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
