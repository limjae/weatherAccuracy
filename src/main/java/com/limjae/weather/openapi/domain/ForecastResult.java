package com.limjae.weather.openapi.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.limjae.weather.openapi.vo.WeatherInfo;
import com.limjae.weather.openapi.vo.enums.LocationEnum;
import com.limjae.weather.openapi.dto.ForecastResponseDto;
import com.limjae.weather.openapi.url.ShortForeCastURI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ForecastResult {
    private final ShortForeCastURI shortForeCastURI;

    public Object getShortForeCastResult() throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(shortForeCastURI.getURI());

        ResponseEntity<String> forEntity = restTemplate.getForEntity(shortForeCastURI.getURI(), String.class);
//        System.out.println("forEntity.getBody() = " + forEntity.getBody());
//        System.out.println("forEntity.getStatusCode() = " + forEntity.getStatusCode());
//        System.out.println("forEntity.getHeaders() = " + forEntity.getHeaders());

        XmlMapper xmlMapper = new XmlMapper();
        Map data = xmlMapper.readValue(forEntity.getBody(), Map.class);


        if (data.containsKey("cmmMsgHeader")) {
            System.out.println("retry after 15 seconds");
            TimeUnit.SECONDS.sleep(15);
            return getShortForeCastResult();
        } else {
            System.out.println("data = " + data.toString());
            ObjectMapper mapper = new ObjectMapper();
            ForecastResponseDto responseDTO = mapper.convertValue(data, ForecastResponseDto.class);
            responseDTO.getBody().getItems().getItem().forEach(i -> System.out.println("info = " + i));
            System.out.println(new WeatherInfo(responseDTO.getBody().getItems()));
        }


        return "";
    }
}
