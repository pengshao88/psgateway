package cn.pengshao.gateway.router;

import cn.pengshao.gateway.handler.HelloHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Description:
 *
 * @Author: yezp
 * @date 2024/5/22 23:10
 */
@Component
public class GatewayRouter {

    @Autowired
    HelloHandler helloHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/hello"), helloHandler::handle);
    }

}
