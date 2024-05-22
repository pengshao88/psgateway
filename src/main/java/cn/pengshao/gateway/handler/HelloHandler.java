package cn.pengshao.gateway.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Description: hello handler
 *
 * @Author: yezp
 * @date 2024/5/22 23:08
 */
@Component
public class HelloHandler {

    public Mono<ServerResponse> handle(ServerRequest request) {
        return ServerResponse.ok().bodyValue("Hello, Spring WebFlux!");
    }

}
