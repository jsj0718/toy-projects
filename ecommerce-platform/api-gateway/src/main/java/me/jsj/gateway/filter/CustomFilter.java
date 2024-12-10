package me.jsj.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        //Netty는 HttpServletRequest, HttpServletResponse를 지원하지 않는다.
        //exchange는 ServerHttpRequest, ServerHttpResponse를 사용할 수 있게 도와주는 객체
        return ((exchange, chain) -> {
            //Netty 요청,응답 조회
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE filter: request id -> {}", request.getId());

            //Custom Post Filter
            //Mono는 Netty 비동기 방식 객체
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                    log.info("Custom POST filter: response code -> {}", response.getStatusCode())));
        });
    }

    public static class Config {
        // Put the configuration properties
    }
}
