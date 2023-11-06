package me.jsj.kakao.contorller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class KakaoMessageController {
    private static final String CLIENT_ID = "REST API KEY";
    private static final String REDIRECT_URI = "REDIRECT URL";
    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public String redirect() {
        String urlStr = String.format("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s", CLIENT_ID, REDIRECT_URI);
        return "redirect:" + urlStr;
    }

    @GetMapping("/dev/kakaoApiTokenMng/oauth")
    @ResponseBody
    public String getTokenAndSendMessage(String code) throws JsonProcessingException {
        Map<String, String> tokenBody = getAccessToken(code);
        sendMessage(tokenBody.get("access_token"), "소재 ID", "광고 계정 ID", "휴대폰 번호");
        return "OK";
    }

    private Map<String, String> getAccessToken(String code) throws JsonProcessingException {
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json");

        LinkedMultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", "authorization_code");
        param.add("client_id", CLIENT_ID);
        param.add("redirect_uri", REDIRECT_URI);
        param.add("code", code);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(param, headers), String.class);

        return objectMapper.readValue(response.getBody(), Map.class);
    }

    private Map<String, String> sendMessage(String accessToken, String creativeId, String adAccountId, String phoneNumber) throws JsonProcessingException {
        String url = String.format("https://apis.moment.kakao.com/openapi/v4/messages/creatives/%s/sendTestPersonalMessage", creativeId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", accessToken));
        headers.add("adAccountId", adAccountId);

        HashMap<String, String> variables = new HashMap<>() {{
            put("date1", "2023-11-06");
            put("date2", "2023-12-31");
            put("site_name1", "네이버");
            put("user_name1", "정대만");
            put("mobile_url1", "https://m.naver.com/");
            put("pc_url1", "https://www.naver.com/");
            put("mobile_url2", "https://m.google.com/");
            put("pc_url2", "https://www.google.com/");
        }};

        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("phoneNumber", phoneNumber);
        body.add("variables", objectMapper.writeValueAsString(variables));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(body, headers), String.class);

        return objectMapper.readValue(response.getBody(), Map.class);
    }
}
