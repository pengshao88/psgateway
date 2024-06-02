package cn.pengshao.gateway.web.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Description: gateway web filter
 *
 * @Author: yezp
 * @date 2024/5/26 22:26
 */
@Component
public class GatewayWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("===>>>> PS Gateway web filter ...");
        if (exchange.getRequest().getQueryParams().getFirst("mock") == null) {
            return chain.filter(exchange);
        }

        String mock = """
                {"result": "mock"}
                """;
        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse().bufferFactory()
                        .wrap(mock.getBytes(StandardCharsets.UTF_8))));
    }
}
