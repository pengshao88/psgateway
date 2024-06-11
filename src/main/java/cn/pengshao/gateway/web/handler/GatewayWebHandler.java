package cn.pengshao.gateway.web.handler;

import cn.pengshao.gateway.filter.GatewayFilter;
import cn.pengshao.gateway.plugin.GatewayPlugin;
import cn.pengshao.gateway.plugin.impl.DefaultGatewayPluginChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Description: gateway web handler
 *
 * @Author: yezp
 * @date 2024/5/26 22:14
 */
@Slf4j
@Component("gatewayWebHandler")
public class GatewayWebHandler implements WebHandler {

    @Autowired
    List<GatewayPlugin> plugins;

    @Autowired
    List<GatewayFilter> filters;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange) {
        log.info("===>>>> PS Gateway web handler ...");
        if (plugins == null || plugins.isEmpty()) {
            String mock = """
                    {"result":"no plugin"}
                    """;

            return exchange.getResponse().writeWith(
                    Mono.just(exchange.getResponse().bufferFactory().wrap(mock.getBytes())));
        }

        for (GatewayFilter filter : filters) {
            filter.filter(exchange);
        }
        return new DefaultGatewayPluginChain(plugins).handle(exchange);
    }
}
