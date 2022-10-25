package com.limjae.weather.entity._global;

import com.limjae.weather.openapi.dto.CommonApiResponseDto;
import com.limjae.weather.openapi.dto.LiveAPIResponseDto;
import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.entity.enums.RainEnum;
import com.limjae.weather.openapi.type.OpenApiType;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Weather {
    private LocalDateTime measuredDate;

    @Enumerated(EnumType.STRING)
    private OpenApiType type;

    @Enumerated(EnumType.STRING)
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
    @Enumerated(EnumType.STRING)
    private RainEnum rainCondition = RainEnum.ERROR;

    public Weather(OpenApiType type, CommonApiResponseDto responseDto) {
        this.type = type;

        List<CommonApiResponseDto.Info> infoList = responseDto.getItems().getItem();
        if (infoList.size() > 0) {
            CommonApiResponseDto.Info info = infoList.get(0);
            this.location = LocationEnum.valueOfPoint(new LocationEnum.Point(info.getNx(), info.getNy()));
            this.measuredDate = LocalDateTime.parse(info.getBaseDate() + info.getBaseTime(),
                    DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        }

        for (CommonApiResponseDto.Info info : infoList) {
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
