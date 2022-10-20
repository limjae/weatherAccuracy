package com.limjae.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.limjae.weather.openapi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherApiController {
    private final OpenApiService openAPIService;

    @RequestMapping("/forecast/live")
    public String shortForecast() throws JsonProcessingException, InterruptedException {

//        openAPIService.loadAllLocation();

        return "Hello";
    }
}
