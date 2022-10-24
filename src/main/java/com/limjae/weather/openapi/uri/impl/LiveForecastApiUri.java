package com.limjae.weather.openapi.uri.impl;

import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.time.impl.LiveTime;
import com.limjae.weather.openapi.uri.OpenApiParameter;
import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.openapi.uri.OpenApiUri;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiveForecastApiUri implements OpenApiUri {
    private static final String baseUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
    private final LiveTime liveTime;
    @Value("${secret_key.encoding.short}")
    private String encodingKey;
    @Value("${secret_key.decoding.short}")
    private String decodingKey;

    @Override
    public OpenApiType getType() {
        return OpenApiType.LIVE;
    }

    @Override
    public URI getURI() {
        OpenApiParameter openApiParameter = new OpenApiParameter(liveTime, LocalDateTime.now().withHour(6), LocationEnum.SEOUL);
        return getURI(openApiParameter);
    }

    @Override
    public URI getURI(OpenApiParameter openApiParameter) {
        try {
            String url = generateUrl(openApiParameter);
            log.debug("Create URL = {}", url);
            return new URI(url);
        } catch (URISyntaxException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            throw new RuntimeException("URL 생성 과정에서 문제가 발생하였습니다.");
        }
    }

    private String generateUrl(OpenApiParameter openApiParameter) {
        if (isInvalidType(openApiParameter)) {
            throw new IllegalArgumentException("파라미터 입력 오류입니다.");
        }
        return baseUrl +
                "?" + URLEncoder.encode("ServiceKey", StandardCharsets.UTF_8) + "=" + encodingKey +
                "&" + URLEncoder.encode("nx", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(openApiParameter.getLocation().getX(), StandardCharsets.UTF_8) + //경도
                "&" + URLEncoder.encode("ny", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(openApiParameter.getLocation().getY(), StandardCharsets.UTF_8) + //위도
                "&" + URLEncoder.encode("base_date", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(openApiParameter.getDate(), StandardCharsets.UTF_8) + /* 조회하고싶은 날짜*/
                "&" + URLEncoder.encode("base_time", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(openApiParameter.getTime(), StandardCharsets.UTF_8) + /* 조회하고싶은 시간 AM 02시부터 3시간 단위 */
                "&" + URLEncoder.encode("dataType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("XML", StandardCharsets.UTF_8);    /* 타입 */
    }

    private boolean isInvalidType(OpenApiParameter openApiParameter) {
        return getType() != openApiParameter.getType();
    }
}
