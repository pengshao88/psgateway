package cn.pengshao.gateway.plugin;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Gateway Plugin Chain
 *
 * @Author: yezp
 * @date 2024/6/2 8:17
 */
public interface GatewayPluginChain {

    Mono<Void> handle(ServerWebExchange exchange);

}
