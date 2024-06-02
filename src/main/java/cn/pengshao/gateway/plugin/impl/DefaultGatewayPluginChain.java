package cn.pengshao.gateway.plugin.impl;

import cn.pengshao.gateway.plugin.GatewayPlugin;
import cn.pengshao.gateway.plugin.GatewayPluginChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Description:
 *
 * @Author: yezp
 * @date 2024/6/2 21:25
 */
public class DefaultGatewayPluginChain implements GatewayPluginChain {

    List<GatewayPlugin> plugins;
    int index = 0;

    public DefaultGatewayPluginChain(List<GatewayPlugin> plugins) {
        this.plugins = plugins;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange) {
        return Mono.defer(() -> {
            if (index < plugins.size()) {
                return plugins.get(index++).handle(exchange, this);
            } else {
                return Mono.empty();
            }
        });
    }
}
