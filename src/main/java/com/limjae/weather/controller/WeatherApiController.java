package com.limjae.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.limjae.weather.entity._global.Weather;
import com.limjae.weather.openapi.service.LiveApiService;
import com.limjae.weather.openapi.service.ShortApiService;
import com.limjae.weather.openapi.type.OpenApiType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WeatherApiController {
    private final LiveApiService liveApiService;
    private final ShortApiService shortApiService;

    @RequestMapping("/forecast/live")
    public String liveForecast() throws JsonProcessingException, InterruptedException {

//        openAPIService.loadAllLocation();

        return "Hello";
    }

    @RequestMapping("/forecast/short")
    public String shortForecast() throws JsonProcessingException, InterruptedException {

        List<Weather> weathers = shortApiService.loadAllLocation(OpenApiType.SHORT, LocalDateTime.now().withHour(6));
        weathers.forEach(weather -> log.info("Weather {}", weather));
        return "Hello";
    }
}
