package com.limjae.weather.openapi.uri.impl;

import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.basetime.OpenApiBaseTime;
import com.limjae.weather.openapi.uri.OpenApiUri;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class LiveForecastURI implements OpenApiUri {
    private static final String baseUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";

    @Value("${secret_key.encoding.short}")
    private String encodingKey;
    @Value("${secret_key.decoding.short}")
    private String decodingKey;

    public URI getURI() {
        try {
            String baseDate = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);

            /* URL */
            String url = baseUrl + "?" +
                    URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(encodingKey, StandardCharsets.UTF_8) + /* 공공데이터포털에서 받은 인증키 */
                    "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8) + /* 페이지번호 */
                    "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("20", StandardCharsets.UTF_8) + /* 한 페이지 결과 수 */
                    "&" + URLEncoder.encode("dataType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("XML", StandardCharsets.UTF_8) + /* 요청자료형식(XML/JSON) Default: XML */
                    "&" + URLEncoder.encode("base_date", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(baseDate, StandardCharsets.UTF_8) + /* ‘21년 6월 28일 발표 */
                    "&" + URLEncoder.encode("base_time", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("0200", StandardCharsets.UTF_8) + /* 02시 발표(정시단위) */
                    "&" + URLEncoder.encode("nx", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("60", StandardCharsets.UTF_8) + /* 예보지점의 X 좌표값 */
                    "&" + URLEncoder.encode("ny", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("127", StandardCharsets.UTF_8); /* 예보지점의 Y 좌표값 */

            return new URI(url);
        } catch (URISyntaxException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            throw new RuntimeException("URL 생성 과정에서 문제가 발생하였습니다.");
        }
    }

    public URI getURI(OpenApiBaseTime baseTime, LocationEnum location) {
        try {
            String url = baseUrl +
                    "?" + URLEncoder.encode("ServiceKey", StandardCharsets.UTF_8) + "=" + encodingKey +
                    "&" + URLEncoder.encode("nx", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(location.getX(), StandardCharsets.UTF_8) + //경도
                    "&" + URLEncoder.encode("ny", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(location.getY(), StandardCharsets.UTF_8) + //위도
                    "&" + URLEncoder.encode("base_date", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(baseTime.getDate(), StandardCharsets.UTF_8) + /* 조회하고싶은 날짜*/
                    "&" + URLEncoder.encode("base_time", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(baseTime.getBaseHourAndMinute(), StandardCharsets.UTF_8) + /* 조회하고싶은 시간 AM 02시부터 3시간 단위 */
                    "&" + URLEncoder.encode("dataType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("XML", StandardCharsets.UTF_8);    /* 타입 */

            log.info("Create URL = {}", url);
            return new URI(url);
        } catch (URISyntaxException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            throw new RuntimeException("URL 생성 과정에서 문제가 발생하였습니다.");
        }
    }

}
