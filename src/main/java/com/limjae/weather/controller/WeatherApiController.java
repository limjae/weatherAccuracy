package com.limjae.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.limjae.weather.openapi.service.OpenAPIService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class WeatherApiController {
    private final OpenAPIService openAPIService;

    @RequestMapping("/short/forecast")
    public String shortForecast() throws JsonProcessingException, InterruptedException {

        openAPIService.loadForeCastToDB();

        return "Hello";
    }
}
