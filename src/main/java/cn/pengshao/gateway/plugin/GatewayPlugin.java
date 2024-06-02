package cn.pengshao.gateway.plugin;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * gateway plugin
 *
 * @Author: yezp
 * @date 2024/6/2 8:16
 */
public interface GatewayPlugin {

    String GATEWAY_PREFIX = "gw";

    void start();

    void stop();

    String getName();

    boolean support(ServerWebExchange exchange);

    Mono<Void> handle(ServerWebExchange exchange, GatewayPluginChain chain);

}
