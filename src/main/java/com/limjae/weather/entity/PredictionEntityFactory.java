package com.limjae.weather.entity;

import com.limjae.weather.entity._global.Weather;
import com.limjae.weather.entity.enums.EntityEnum;

public class PredictionEntityFactory {
    public static PredictionEntity construct(EntityEnum entityType, Weather weather) {
        return switch (entityType) {
            case LIVE -> new Live(weather);
            case D1 -> new Day1(weather);
            case D3 -> new Day3(weather);
            case D7 -> new Day7(weather);
            default -> throw new IllegalArgumentException("Wrong Entity Type");
        };
    }
}
