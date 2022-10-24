package com.limjae.weather.batch.tasklet;

import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.service.Day1Service;
import com.limjae.weather.service.LiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@StepScope
@Slf4j
@RequiredArgsConstructor
public class LoadAfter1DayWithShortApiTasklet implements Tasklet {

    @Value("#{jobParameters[executeHour]}")
    private String executeHour;
    private final Day1Service day1Service;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info(">>>>> Loading 1 Day After Prediction From Short Forecast Api");
        log.info("Parameter executeHour: {}", executeHour);
        LocalDateTime dateTime = LocalDateTime.now().withHour(Integer.parseInt(executeHour));
        day1Service.loadAllToDB(OpenApiType.SHORT, dateTime);
        return RepeatStatus.FINISHED;
    }
}
