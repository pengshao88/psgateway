//package cn.pengshao.gateway.web.router;
//
//import cn.pengshao.gateway.web.handler.GatewayHandler;
//import cn.pengshao.gateway.web.handler.HelloHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.RouterFunction;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//
///**
// * Description:
// *
// * @Author: yezp
// * @date 2024/5/22 23:10
// */
//@Component
//public class GatewayRouter {
//
//    @Autowired
//    HelloHandler helloHandler;
//
//    @Autowired
//    GatewayHandler gatewayHandler;
//
//    @Bean
//    public RouterFunction<?> routerFunction() {
//        return route(GET("/hello"), helloHandler::handle);
//    }
//
//    @Bean
//    public RouterFunction<?> gatewayRouterFunction() {
//        return route(GET("/gw").or(POST("/gw/**")), gatewayHandler::handle);
//    }
//
//}
