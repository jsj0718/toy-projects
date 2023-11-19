package me.jsj.cafe24.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jsj.cafe24.controller.dto.Cafe24AuthDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("dev")
public class Cafe24ApiController {
    private static final String REDIRECT_URI = "REDIRECT_URI";
    private static final String SCOPE = "mall.write_category, mall.read_category";

    private final ObjectMapper objectMapper;

    @GetMapping("cafe24Mng/home")
    public String requestCafe24(Cafe24AuthDto.Home reqDto) {
        String requestUrl = String.format("https://%s.cafe24api.com/api/v2/oauth/authorize?response_type=code&client_id=%s&state=%s&redirect_uri=%s&scope=%s",
                reqDto.getMallId(), reqDto.getClientId(), getState(reqDto), REDIRECT_URI, SCOPE);
        return "redirect:" + requestUrl;
    }

    private String getState(Cafe24AuthDto.Home reqDto) {
        try {
            return objectMapper.writeValueAsString(reqDto);
        } catch (JsonProcessingException e) {
            log.error(">>>>> json to string 변환 실패: {}", reqDto);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("cafe24Mng/oauth")
    public String cafe24oauth(Cafe24AuthDto.Auth auth) {
        Cafe24AuthDto.Home home = getHome(auth);

        String requestUrl = String.format("https://%s.cafe24api.com/api/v2/oauth/token", home.getMallId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + home.encode());
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", auth.getCode());
        body.add("redirect_uri", REDIRECT_URI);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Cafe24AuthDto.AccessToken> response = restTemplate.postForEntity(requestUrl, new HttpEntity<>(body, headers), Cafe24AuthDto.AccessToken.class);

        log.info("[HttpStatus: {}] response body: {}", response.getStatusCode(), response.getBody());

        return response.getBody().getAccessToken();
    }

    private Cafe24AuthDto.Home getHome(Cafe24AuthDto.Auth auth) {
        try {
            return objectMapper.readValue(auth.getState(), Cafe24AuthDto.Home.class);
        } catch (JsonProcessingException e) {
            log.error(">>>>> string to json 변환 실패: {}", auth);
            throw new RuntimeException(e);
        }
    }

}
