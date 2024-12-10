package me.jsj.firstservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/first-service")
@RestController
public class FirstController {

    private final Environment env;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to First Service!";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header) {
        log.info("header: {}", header);
        return "Logging to First Service!";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("server port: {}", request.getServerPort());
        return String.format("This is a message from First Service on PORT %s", env.getProperty("local.server.port"));
    }
}