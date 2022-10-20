package com.limjae.weather.openapi.time;

import com.limjae.weather.openapi.type.OpenApiType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class OpenApiTimeFactory {

    private final Map<OpenApiType, OpenApiTime> baseTimeMap = new HashMap<>();

    public OpenApiTimeFactory(List<OpenApiTime> baseTimeList) {
        if (CollectionUtils.isEmpty(baseTimeList)) {
            throw new IllegalArgumentException("존재하는 OpenApiTime 구현체가 없음");
        }

        for (OpenApiTime baseTime : baseTimeList) {
            this.baseTimeMap.put(baseTime.getType(), baseTime);
        }
    }

    public Set<OpenApiType> getTypes() {
        return baseTimeMap.keySet();
    }

    public OpenApiTime generate(OpenApiType type) {
        return baseTimeMap.get(type);
    }
}
