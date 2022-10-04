package com.limjae.weather.openapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class ForecastResponseDto {
    private Header header;
    private Body body;

    @Data
    public static class Header {
        private long resultCode;
        private String resultMsg;
    }

    @Data
    public static class Body {
        private String dataType;
        private Items items;
        private long numOfRows;
        private long pageNo;
        private long totalCount;
    }

    @Data
    public static class Items {
        private List<WeatherInfo> item;
    }

    @Data
    public static class WeatherInfo {
        private String baseDate;
        private String baseTime;
        private String category;
        private String nx;
        private String ny;
        double obsrValue;
    }


}

