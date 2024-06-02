package cn.pengshao.gateway.filter;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * gateway filter
 *
 * @Author: yezp
 * @date 2024/6/2 21:20
 */
public interface GatewayFilter {

    Mono<Void> filter(ServerWebExchange exchange);

}
