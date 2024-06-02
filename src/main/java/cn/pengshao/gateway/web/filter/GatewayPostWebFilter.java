package cn.pengshao.gateway.web.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Description:后置过滤器，在返回结果之后
 *
 * @Author: yezp
 * @date 2024/5/26 22:30
 */
@Component
public class GatewayPostWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).doFinally(
                s -> {
                    System.out.println("===>>>> post filter");
                    exchange.getAttributes().forEach((k, v) -> System.out.println(k + ":" + v));
                }
        );
    }
}
