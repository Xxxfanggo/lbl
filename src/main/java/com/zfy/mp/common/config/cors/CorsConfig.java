package com.zfy.mp.common.config.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 *
 * @文件名: CrosConfig.java
 * @工程名: mp
 * @包名: com.zfy.mp.config.cros
 * @描述: 跨域配置
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-15 13:51
 * @版本号: V2.4.0
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
