package cn.pengshao.gateway.filter.impl;

import cn.pengshao.gateway.filter.GatewayFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * demo filter
 *
 * @Author: yezp
 * @date 2024/6/2 21:54
 */
@Slf4j
@Component("demoFilter")
public class DemoFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange) {
        log.info(" ===>>> filters: demo filter ...");
        exchange.getRequest().getHeaders().toSingleValueMap()
                .forEach((k, v) -> log.info("key: {}, value: {}", k, v));
        return Mono.empty();
    }

}
