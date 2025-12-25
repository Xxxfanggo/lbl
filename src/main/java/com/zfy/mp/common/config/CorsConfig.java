package com.zfy.mp.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

/***
 * 作用范围：仅适用于 Spring MVC 的请求处理
 * 过滤器链位置：在 Spring Security 过滤器链之后执行
 * 问题：当请求到达 JwtAuthenticationTokenFilter 时，CORS 配置还未生效
 *
 ****/
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("Authorization")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
//}

/***
 * 作用范围：在 Spring Security 过滤器链中生效
 * 过滤器链位置：与 Spring Security 过滤器集成
 * 优势：预检请求和认证请求都能正确处理
 *
 ****/
@Configuration
public class CorsConfig  {
    private static final Logger log = LoggerFactory.getLogger(CorsConfig.class);

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");  // 或指定具体域名
        configuration.addAllowedHeader("Authorization");  // 允许 Authorization 头
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}