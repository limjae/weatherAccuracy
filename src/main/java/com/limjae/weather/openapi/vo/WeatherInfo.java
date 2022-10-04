package com.limjae.weather.openapi.vo;

import com.limjae.weather.openapi.dto.ForecastResponseDto;
import com.limjae.weather.openapi.vo.enums.LocationEnum;
import com.limjae.weather.openapi.vo.enums.RainEnum;
import com.limjae.weather.openapi.vo.enums.SKYEnum;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class WeatherInfo {
    private LocationEnum location = LocationEnum.UNKNOWN;
    // celsius
    private double temperature = 0.0;
    // mm/h
    private double rainPerHour;
    // m/s
    private double windSpeedHorizontal;
    // m/s
    private double windSpeedVertical;
    // %
    private double humidity;
    // degree
    private double windDirection;
    // m/s
    private double windSpeed;
    // PTY
    private RainEnum rainCondition;

    public WeatherInfo(ForecastResponseDto.Items items) {
        List<ForecastResponseDto.WeatherInfo> infoList = items.getItem();
        if (infoList.size() > 0){
            ForecastResponseDto.WeatherInfo info = infoList.get(0);
            this.location = LocationEnum.valueOfPoint(new LocationEnum.Point(info.getNx(), info.getNy()));
        }

        for (ForecastResponseDto.WeatherInfo info : infoList) {
            switch (info.getCategory()) {
                case "T1H" -> this.temperature = info.getObsrValue();
                case "RN1" -> this.rainPerHour = info.getObsrValue();
                case "UUU" -> this.windSpeedHorizontal = info.getObsrValue();
                case "VVV" -> this.windSpeedVertical = info.getObsrValue();
                case "REH" -> this.humidity = info.getObsrValue();
                case "PTY" -> this.rainCondition = RainEnum.valueOfCode((int) info.getObsrValue());
                case "VEC" -> this.windDirection = info.getObsrValue();
                case "WSD" -> this.windSpeed = info.getObsrValue();
            }
        }
    }
}
