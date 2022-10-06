package com.limjae.weather.openapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenAPIResponseDto {
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
        private List<Info> item;
    }

    @Data
    public static class Info {
        private String baseDate;
        private String baseTime;
        private String category;
        private String nx;
        private String ny;
        double obsrValue;
    }


}

