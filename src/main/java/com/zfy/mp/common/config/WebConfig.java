package com.zfy.mp.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 *
 * @文件名: WebConfig.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.config.cors
 * @描述: WebMvc配置
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-15 14:37
 * @版本号: V2.4.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        configurer.addPathPrefix("/lbl", c -> true);
//    }
    // 使用 JwtAuthenticationTokenFilter 过滤
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new JWTInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/login",
//                        "/oauth2/**",
//                        "/oauth2/github/callback",
//                        "/lbl/oauth2/github/callback");
//    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}
