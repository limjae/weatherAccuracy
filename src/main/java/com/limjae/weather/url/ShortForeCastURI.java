package com.limjae.weather.url;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Component
public class ShortForeCastURI {
    private static String baseUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";

    @Value("${secret_key.encoding.short}")
    private String encodingKey;
    @Value("${secret_key.decoding.short}")
    private String decodingKey;

    public URI getURI() throws UnsupportedEncodingException, URISyntaxException {
        String baseDate = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        /* URL */
        String url = baseUrl + "?" +
                URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(encodingKey, "UTF-8") + /* 공공데이터포털에서 받은 인증키 */
                "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + /* 페이지번호 */
                "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8") + /* 한 페이지 결과 수 */
                "&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8") + /* 요청자료형식(XML/JSON) Default: XML */
                "&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8") + /* ‘21년 6월 28일 발표 */
                "&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode("0200", "UTF-8") + /* 02시 발표(정시단위) */
                "&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode("55", "UTF-8") + /* 예보지점의 X 좌표값 */
                "&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode("127", "UTF-8"); /* 예보지점의 Y 좌표값 */

        return new URI(url);
    }

}
