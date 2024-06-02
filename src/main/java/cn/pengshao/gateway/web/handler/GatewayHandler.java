//package cn.pengshao.gateway.web.handler;
//
//import cn.pengshao.rpc.core.api.LoadBalancer;
//import cn.pengshao.rpc.core.cluster.RoundLoadBalancer;
//import cn.pengshao.rpc.core.meta.InstanceMeta;
//import cn.pengshao.rpc.core.meta.ServiceMeta;
//import cn.pengshao.rpc.core.registry.RegistryCenter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
///**
// * Description: gateway handler
// *
// * @Author: yezp
// * @date 2024/5/23 22:42
// */
//@Component
//public class GatewayHandler {
//
//    @Autowired
//    RegistryCenter rc;
//
//    LoadBalancer<InstanceMeta> loadBalancer = new RoundLoadBalancer<>();
//
//    // curl -H "Content-Type: application/json" -X POST
//    // -d '{"service":"cn.pengshao.rpc.demo.api.UserService", "methodSign":"findById_Integer", "args":[100]}'
//    // http://localhost:8888/gw/cn.pengshao.rpc.demo.api.UserService
//
//    public Mono<ServerResponse> handle(ServerRequest request) {
//        // 1、通过请求路径获取服务名
//        String service = request.path().substring(4);
//        ServiceMeta serviceMeta = ServiceMeta.builder()
//                .name(service)
//                .app("psrpc")
//                .namespace("public")
//                .env("dev")
//                .version("1.0.0")
//                .build();
//        // 2、通过 rc 获取服务实例列表
//        List<InstanceMeta> instanceMetas = rc.fetchAll(serviceMeta);
//
//        // 3、通过 loadBalancer 获取服务实例
//        InstanceMeta instanceMeta = loadBalancer.choose(instanceMetas);
//        System.out.println("choose inst : " + instanceMeta);
//        String url = instanceMeta.toUrl();
//
//        // 4、拿到请求的报文
//        Mono<String> reqestMono = request.bodyToMono(String.class);
//        return reqestMono.flatMap(body -> invokeFromRegistry(body, url));
//    }
//
//    private Mono<? extends ServerResponse> invokeFromRegistry(String body, String url) {
//        // 5、通过 webClient 发送请求
//        WebClient webClient = WebClient.create(url);
//        Mono<ResponseEntity<String>> entity = webClient.post()
//                .header("Content-Type", "application/json")
//                .bodyValue(body)
//                .retrieve()
//                .toEntity(String.class);
//
//        // 6、通过entity获取响应报文
//        Mono<String> responseBody = entity.map(ResponseEntity::getBody);
//        // subscribe()方法是触发实际操作的关键，没有调用subscribe()，WebClient不会发送请求，也不会拿到返回的结果。
//        responseBody.subscribe(source -> System.out.println("responseBody : " + source));
//
//        // 7、组装并返回响应报文
//        return ServerResponse.ok()
//                .header("Content-Type", "application/json")
//                .header("ps.gw.version", "v1.0.0")
//                .body(responseBody, String.class);
//    }
//}
