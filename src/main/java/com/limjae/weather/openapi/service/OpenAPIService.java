package com.limjae.weather.openapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.limjae.weather.entity.ActualWeather;
import com.limjae.weather.entity.enums.ForecastTypeEnum;
import com.limjae.weather.openapi.url.LongForeCastURI;
import com.limjae.weather.entity._global.WeatherInfo;
import com.limjae.weather.openapi.dto.OpenAPIResponseDto;
import com.limjae.weather.openapi.url.ShortForeCastURI;
import com.limjae.weather.repository.ActualWeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAPIService {
    private final ShortForeCastURI shortForeCastURI;
    private final LongForeCastURI longForeCastURI;

    private final ActualWeatherRepository actualWeatherRepository;

    public WeatherInfo loadShortForeCast() throws JsonProcessingException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(shortForeCastURI.getURI(), String.class);

        XmlMapper xmlMapper = new XmlMapper();
        Map data = xmlMapper.readValue(forEntity.getBody(), Map.class);


        if (data.containsKey("cmmMsgHeader")) {
            log.info("retry after 15 seconds");
            TimeUnit.SECONDS.sleep(15);
            return loadShortForeCast();
        } else {
            ObjectMapper mapper = new ObjectMapper();
            OpenAPIResponseDto responseDTO = mapper.convertValue(data, OpenAPIResponseDto.class);
            responseDTO.getBody().getItems().getItem().forEach(i -> System.out.println("info = " + i));

            responseDTO.getBody().getItems().getItem();
            return new WeatherInfo(responseDTO.getBody().getItems());

        }
    }

    @Transactional
    public ActualWeather loadForeCastToDB() throws JsonProcessingException, InterruptedException {
        return actualWeatherRepository.save(new ActualWeather(loadShortForeCast(), ForecastTypeEnum.SHORT));
    }

}
