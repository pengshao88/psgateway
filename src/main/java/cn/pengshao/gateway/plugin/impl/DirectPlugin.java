package cn.pengshao.gateway.plugin.impl;

import cn.pengshao.gateway.plugin.AbstractGatewayPlugin;
import cn.pengshao.gateway.plugin.GatewayPluginChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Description:
 *
 * @Author: yezp
 * @date 2024/6/2 21:32
 */
@Slf4j
@Component("directPlugin")
public class DirectPlugin extends AbstractGatewayPlugin {

    private static final String NAME = "direct";
    private static final String PREFIX = GATEWAY_PREFIX + "/" + NAME + "/";

    @Override
    public Mono<Void> doHandle(ServerWebExchange exchange, GatewayPluginChain chain) {
        log.info("=======>>>>>>> [DirectPlugin] ...");
        String backend = exchange.getRequest().getQueryParams().getFirst("backend");
        Flux<DataBuffer> requestBody = exchange.getRequest().getBody();
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        exchange.getResponse().getHeaders().add("ps.gw.version", "v1.0.0");
        exchange.getResponse().getHeaders().add("ps.gw.plugin", getName());

        if (backend == null || backend.isEmpty()) {
            // 还不是可以直接 chain.handle(exchange)
            return requestBody.flatMap(x -> exchange.getResponse().writeWith(Mono.just(x)))
                    .then(chain.handle(exchange));
        }

        WebClient client = WebClient.create(backend);
        Mono<ResponseEntity<String>> entity = client.post()
                .header("Content-Type", "application/json")
                .body(requestBody, DataBuffer.class).retrieve().toEntity(String.class);
        Mono<String> body = entity.map(ResponseEntity::getBody);
        return body.flatMap(x -> exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse().bufferFactory().wrap(x.getBytes()))))
                .then(chain.handle(exchange));
    }

    @Override
    public boolean doSupport(ServerWebExchange exchange) {
        return exchange.getRequest().getPath().value().startsWith(PREFIX);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
