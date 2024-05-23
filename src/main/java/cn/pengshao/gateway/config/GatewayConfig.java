package cn.pengshao.gateway.config;

import cn.pengshao.rpc.core.registry.RegistryCenter;
import cn.pengshao.rpc.core.registry.ps.PsRegistryCenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
