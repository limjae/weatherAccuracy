package com.limjae.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.limjae.weather.entity._global.Weather;
import com.limjae.weather.openapi.OpenApi;
import com.limjae.weather.openapi.type.OpenApiType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JUST FOR TEST--
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class WeatherApiController {
    private final OpenApi openApi;

    @RequestMapping("/forecast/live")
    public String liveForecast() throws JsonProcessingException, InterruptedException {
        List<Weather> weathers = openApi.loadAllLocation(OpenApiType.LIVE, LocalDateTime.now().withHour(6));
        weathers.forEach(weather -> log.info("Weather {}", weather));

        return "Hello";
    }

    @RequestMapping("/forecast/short")
    public String shortForecast() throws JsonProcessingException, InterruptedException {

        List<Weather> weathers = openApi.loadAllLocation(OpenApiType.SHORT, LocalDateTime.now().withHour(6));
        weathers.forEach(weather -> log.info("Weather {}", weather));
        return "Hello";
    }
}
