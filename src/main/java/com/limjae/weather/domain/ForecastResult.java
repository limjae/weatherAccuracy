package com.limjae.weather.domain;

import com.limjae.weather.url.ShortForeCastURI;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ForecastResult {
    private final ShortForeCastURI shortForeCastURI;

    public Object getShortForeCastResult() throws UnsupportedEncodingException, URISyntaxException, ParseException {
        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println(shortForeCastURI.getURI());

        String result = restTemplate.getForObject(shortForeCastURI.getURI(), String.class);
        System.out.println("result = " + result);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result); // API를 통해 넘어온 JSON을 파싱
        System.out.println("jsonObject = " + jsonObject.toJSONString());
        JSONObject response = (JSONObject) jsonObject.get("response"); // JSON 파싱 데이터에서 tracks부분을 배열로 가져옴
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray array = (JSONArray) items.get("item");

        System.out.println("items = " + array.toJSONString());

        return "";
    }
}
