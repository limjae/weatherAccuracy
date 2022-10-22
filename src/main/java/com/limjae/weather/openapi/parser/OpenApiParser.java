package com.limjae.weather.openapi.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.limjae.weather.openapi.dto.CommonApiResponseDto;
import com.limjae.weather.openapi.type.OpenApiType;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface OpenApiParser {
    public OpenApiType getType();
    public Map<?, ?> xmlResponseToMap(ResponseEntity<String> responseEntity) throws JsonProcessingException;
    public CommonApiResponseDto mapToResponseDto(Map<?, ?> data) throws IllegalArgumentException;
}
