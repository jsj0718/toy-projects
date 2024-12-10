package me.jsj.secondservice;

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
@RequestMapping("/second-service")
@RestController
public class SecondController {

    private final Environment env;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Second Service!";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        log.info("header: {}", header);
        return "Logging to Second Service!";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("server port: {}", request.getServerPort());
        return String.format("This is a message from Second Service on PORT %s", env.getProperty("local.server.port"));
    }
}
