package com.limjae.weather.openapi.uri.impl;

import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.time.impl.MidtermTime;
import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.openapi.uri.OpenApiParameter;
import com.limjae.weather.openapi.uri.OpenApiUri;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class MidtermForecastApiUri implements OpenApiUri {
    private static final String baseUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService";
    private final MidtermTime midtermTime;
    @Value("${secret_key.encoding.short}")
    private String encodingKey;
    @Value("${secret_key.decoding.short}")
    private String decodingKey;

    @Override
    public OpenApiType getType() {
        return OpenApiType.MIDTERM;
    }

    @Override
    public URI getURI() {
        OpenApiParameter openApiParameter = new OpenApiParameter(midtermTime, LocalDateTime.now().plusDays(3).withHour(9), LocationEnum.SEOUL);
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
                "&" + URLEncoder.encode("regId", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(openApiParameter.getLocation().getX(), StandardCharsets.UTF_8) + //경도
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1000", StandardCharsets.UTF_8) + //출력물수
                "&" + URLEncoder.encode("tmFc", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(openApiParameter.getDate() + openApiParameter.getTime(), StandardCharsets.UTF_8) + /* 기준 일자 */
                "&" + URLEncoder.encode("dataType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("XML", StandardCharsets.UTF_8);   /* 출력 타입 */
    }

    private boolean isInvalidType(OpenApiParameter openApiParameter) {
        return getType() != openApiParameter.getType();
    }
}
