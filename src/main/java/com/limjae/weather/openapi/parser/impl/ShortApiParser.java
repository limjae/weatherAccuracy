package com.limjae.weather.openapi.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.limjae.weather.openapi.dto.CommonApiResponseDto;
import com.limjae.weather.openapi.dto.ShortAPIResponseDto;
import com.limjae.weather.openapi.parser.OpenApiParser;
import com.limjae.weather.openapi.type.OpenApiType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ShortApiParser implements OpenApiParser {

    @Override
    public OpenApiType getType() {
        return OpenApiType.SHORT;
    }

    @Override
    public Map<?, ?> xmlResponseToMap(ResponseEntity<String> responseEntity) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        Map<?, ?> data = xmlMapper.readValue(responseEntity.getBody(), Map.class);
        log.debug("Api responseEntity: {}", responseEntity);
        log.debug("Api data: {}", data);
        return data;
    }

    @Override
    public CommonApiResponseDto mapToResponseDto(Map<?, ?> data, int predictionDay) throws IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        ShortAPIResponseDto shortAPIResponseDto = mapper.convertValue(data, ShortAPIResponseDto.class);

        if (shortAPIResponseDto.getHeader().getResultCode() == 01) {
            throw new IllegalArgumentException("Api Parameter Error");
        } else if (shortAPIResponseDto.getHeader().getResultCode() != 00) {
            throw new IllegalArgumentException("API 오류 입니다: " + data.toString());
        }

        return new CommonApiResponseDto(shortAPIResponseDto, predictionDay);

    }
}
