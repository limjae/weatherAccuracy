package com.limjae.weather.batch.tasklet;

import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
@RequiredArgsConstructor
public class LoadLiveApiTasklet implements Tasklet {

    @Value("#{jobParameters[executeHour]}")
    private String executeHour;
    private final WeatherService weatherService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info(">>>>> Loading From Live Forecast Api");
        log.info("Parameter executeHour: {}", executeHour);
        weatherService.loadToDB(OpenApiType.LIVE, Integer.parseInt(executeHour));
        return RepeatStatus.FINISHED;
    }
}
