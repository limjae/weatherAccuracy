package com.limjae.weather.batch.tasklets;

import com.limjae.weather.openapi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherTasklet implements Tasklet {
    private final OpenApiService openAPIService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {                //business 업무 시작        for(int inx = 0 ; inx < 20 ; inx++) {            log.info("[step1] : " + inx);        }         //business 업무 끝        return RepeatStatus.FINISHED;    }}
        openAPIService.loadAllLocation();


        return RepeatStatus.FINISHED;
    }
}