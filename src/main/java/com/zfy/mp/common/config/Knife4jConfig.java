package com.zfy.mp.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 *
 * @文件名: Knife4jConfig.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.config
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-13 14:52
 * @版本号: V2.4.0
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API文档")
                        .description("项目API文档")
                        .version("v1.0"))
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8089").description("开发环境")));
    }

}
