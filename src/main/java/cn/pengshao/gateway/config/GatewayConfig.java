package cn.pengshao.gateway.config;

import cn.pengshao.rpc.core.registry.RegistryCenter;
import cn.pengshao.rpc.core.registry.ps.PsRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import java.util.Properties;

/**
 * Description:gateway config
 *
 * @Author: yezp
 * @date 2024/5/23 22:40
 */
@Configuration
public class GatewayConfig {

    @Bean
    public RegistryCenter rc() {
        return new PsRegistryCenter();
    }

    @Bean
    ApplicationRunner runner(@Autowired ApplicationContext context) {
        return args -> {
            SimpleUrlHandlerMapping handlerMapping = context.getBean(SimpleUrlHandlerMapping.class);
            Properties mappings = new Properties();
            mappings.put("/gw/**", "gatewayWebHandler");
            handlerMapping.setMappings(mappings);
            handlerMapping.initApplicationContext();
            System.out.println("psrpc gateway start.");
        };
    }

}
