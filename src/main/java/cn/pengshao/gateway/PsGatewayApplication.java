package cn.pengshao.gateway;

import cn.pengshao.rpc.core.config.PsRegistryProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Description:
 *
 * @Author: yezp
 * @date 2024/5/22 23:05
 */
@SpringBootApplication
@Import({PsRegistryProperties.class})
public class PsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsGatewayApplication.class, args);
    }

}
