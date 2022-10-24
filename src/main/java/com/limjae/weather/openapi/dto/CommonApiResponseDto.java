package com.limjae.weather.openapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class CommonApiResponseDto {
    private Items items = new Items();


    @Data
    public static class Items {
        private List<Info> item;
    }

    @Data
    @AllArgsConstructor
    public static class Info {
        private String baseDate;
        private String baseTime;
        private String category;
        private String nx;
        private String ny;
        private double obsrValue;
    }

    public CommonApiResponseDto(LiveAPIResponseDto liveAPIResponseDto) {
        this.items.setItem(new ArrayList<>());
        List<LiveAPIResponseDto.Info> item = liveAPIResponseDto.getBody().getItems().getItem();
        for (LiveAPIResponseDto.Info info : item) {
            this.items.getItem().add(new Info(
                    info.getBaseDate(),
                    info.getBaseTime(),
                    info.getCategory(),
                    info.getNx(),
                    info.getNy(),
                    info.getObsrValue()
            ));
        }
    }

    public CommonApiResponseDto(ShortAPIResponseDto shortAPIResponseDto, int predictionDay) {
        this.items.setItem(new ArrayList<>());
        List<ShortAPIResponseDto.Info> item = shortAPIResponseDto.getBody().getItems().getItem();
        for (ShortAPIResponseDto.Info info : item) {
            LocalDate forecastDate = LocalDate
                    .parse(info.getFcstDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            LocalDate baseDate = LocalDate
                    .parse(info.getBaseDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));

            int dateDifference = calculateDateDifference(baseDate, forecastDate);

            if (dateDifference == predictionDay
                    && info.getBaseTime().equals(info.getFcstTime())) {

                if (info.getCategory().equals("PCP")) {
                    info.setCategory("RN1");
                    if (info.getFcstValue().equals("강수없음")) {
                        info.setFcstValue("0");
                    }
                } else if (info.getCategory().equals("TMP")) {
                    info.setCategory("T1H");
                } else if (info.getCategory().equals("WAV")
                        || info.getCategory().equals("SNO")
                        || info.getCategory().equals("TMN")
                        || info.getCategory().equals("TMX")
                        || info.getCategory().equals("SKY")
                        || info.getCategory().equals("POP")) {
                    continue;
                }

                this.items.getItem().add(new Info(
                        info.getFcstDate(),
                        info.getFcstTime(),
                        info.getCategory(),
                        info.getNx(),
                        info.getNy(),
                        Double.parseDouble(info.getFcstValue())
                ));
            }
        }

//        System.out.println("items.getItem() = " + items.getItem());
//        System.out.println("items.getItem().size() = " + items.getItem().size());
    }

    private int calculateDateDifference(LocalDate base, LocalDate forecast) {
        if (base.getYear() == forecast.getYear()) {
            return forecast.getDayOfYear() - base.getDayOfYear();
        } else {
            return base.lengthOfYear() + forecast.getDayOfYear() - base.getDayOfYear();
        }
    }

}
