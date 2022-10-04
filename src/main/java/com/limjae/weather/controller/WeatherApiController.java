package com.limjae.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.limjae.weather.openapi.domain.ForecastResult;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class WeatherApiController {
    private final ForecastResult forecastResult;

    @RequestMapping("/short/forecast")
    public String shortForecast() throws UnsupportedEncodingException, URISyntaxException, ParseException, JsonProcessingException, InterruptedException {

        Object shortForeCastResult = forecastResult.getShortForeCastResult();
        System.out.println("forecastResult = " + shortForeCastResult.toString());

        return "Hello";
    }
}
