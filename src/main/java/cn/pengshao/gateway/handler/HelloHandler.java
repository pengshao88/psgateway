package cn.pengshao.gateway.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
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
        String url = "http://localhost:8081/psrpc";
        String requestJson = """
                {
                    "service":"cn.pengshao.rpc.demo.api.UserService",
                    "methodSign":"findById_Integer",
                    "args":[100]
                }
                """;

        WebClient client = WebClient.create(url);
        Mono<ResponseEntity<String>> entity = client.post()
                .header("Content-Type", "application/json")
                .bodyValue(requestJson)
                .retrieve()
                .toEntity(String.class);

        Mono<String> body = entity.map(ResponseEntity::getBody);
        body.subscribe(source -> System.out.println("response:" + source));
        return ServerResponse.ok()
                .header("Content-Type", "application/json")
                .header("ps.gw.version", "v1.0.0")
                .body(body, String.class);
//        return ServerResponse.ok().bodyValue("Hello, Spring WebFlux!");
    }

}
