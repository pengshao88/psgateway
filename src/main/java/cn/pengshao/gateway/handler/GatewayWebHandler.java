package cn.pengshao.gateway.handler;

import cn.pengshao.rpc.core.api.LoadBalancer;
import cn.pengshao.rpc.core.cluster.RoundLoadBalancer;
import cn.pengshao.rpc.core.meta.InstanceMeta;
import cn.pengshao.rpc.core.meta.ServiceMeta;
import cn.pengshao.rpc.core.registry.RegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Description: gateway web handler
 *
 * @Author: yezp
 * @date 2024/5/26 22:14
 */
@Component("gatewayWebHandler")
public class GatewayWebHandler implements WebHandler {

    @Autowired
    RegistryCenter rc;

    LoadBalancer<InstanceMeta> loadBalancer = new RoundLoadBalancer<>();

    /**
     * webHandle
     *
     * @param exchange 有点类似于 上下文
     * @return result
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange) {
        System.out.println("===>>>> PS Gateway web handler ...");
        // 1、通过请求路径获取服务名
        String service = exchange.getRequest().getPath().value().substring(4);
        ServiceMeta serviceMeta = ServiceMeta.builder()
                .name(service)
                .app("psrpc")
                .namespace("public")
                .env("dev")
                .version("1.0.0")
                .build();
        // 2、通过 rc 获取服务实例列表
        List<InstanceMeta> instanceMetas = rc.fetchAll(serviceMeta);

        // 3、通过 loadBalancer 获取服务实例
        InstanceMeta instanceMeta = loadBalancer.choose(instanceMetas);
        System.out.println("choose inst : " + instanceMeta);
        String url = instanceMeta.toUrl();

        // 4、拿到请求的报文
        Flux<DataBuffer> requestBody = exchange.getRequest().getBody();

        // 5、通过 webClient 发送请求
        WebClient webClient = WebClient.create(url);
        Mono<ResponseEntity<String>> entity = webClient.post()
                .header("Content-Type", "application/json")
                .body(requestBody, DataBuffer.class)
                .retrieve()
                .toEntity(String.class);

        // 6、通过entity获取响应报文
        Mono<String> responseBody = entity.map(ResponseEntity::getBody);

        // 7、组装并返回响应报文
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        exchange.getResponse().getHeaders().add("ps.gw.version", "v1.0.0");
        return responseBody.flatMap(body -> exchange.getResponse()
                        .writeWith(Mono.just(exchange.getResponse().bufferFactory()
                                .wrap(body.getBytes(StandardCharsets.UTF_8)))));
    }
}
