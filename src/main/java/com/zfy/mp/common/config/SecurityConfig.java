package com.zfy.mp.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zfy.mp.common.filter.JwtAuthenticationTokenFilter;
import com.zfy.mp.service.user.SysUserDetailsService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @文件名: SecurityConfig.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.config
 * @描述: security配置类
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-16 11:12
 * @版本号: V2.4.0
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Resource
    private SysUserDetailsService sysUserDetailsService;
    @Resource
    private CorsConfigurationSource corsConfigurationSource;



    /**
 * 配置Spring Security的安全过滤器链
 * @param http HttpSecurity对象，用于配置安全设置
 * @return 配置好的SecurityFilterChain实例
 * @throws Exception 可能抛出的异常
 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationTokenFilter jwtFilter) throws Exception {
    // 配置HTTP请求授权规则
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
            // 允许所有路径的匿名访问
                authorize
                    // 允许特定路径（登录和GitHub回调）的匿名访问
                        .requestMatchers("/login", "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                    // 所有其他请求都需要身份验证
        )
            // 配置CSRF防护  jwt获取token可以disable掉csrf
                .csrf(AbstractHttpConfigurer::disable)
//                        .ignoringRequestMatchers("/login","/lbl/login",
//                                "/oauth2/**",
//                                "/lbl/oauth2/github/**",
//                                "/oauth2/github/callback")  // 忽略特定路径的CSRF检查
//                )
                // 禁止默认的表单登录，从 loginController 跳转
                .formLogin(AbstractHttpConfigurer::disable
//                        .loginPage("/home")
//                        .loginProcessingUrl("/login")
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .successHandler((request, response, authentication) -> {
//                            response.setContentType("application/json;charset=utf-8");
//                            response.getWriter().write("{\"code\":200,\"message\":\"登录成功\"}");
//                        })
//                        .failureHandler((request, response, exception) -> {
//                            response.setContentType("application/json;charset=utf-8");
//                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                            response.getWriter().write("{\"code\":401,\"message\":\"登录失败\"}");
//                        })
                       )
                .oauth2Login(oauth2 -> oauth2
                                .loginPage("/login")
                        .defaultSuccessUrl("/home")
                                .redirectionEndpoint(redirection ->
                                redirection
                                        .baseUri("/lbl/oauth2/{registrationId}/callback"))
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler())
                        .accessDeniedHandler(accessDeniedHandler()));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(sysUserDetailsService);
        // 将使用的密码编译器加入进来
        provider.setPasswordEncoder(passwordEncoder);
        ProviderManager providerManager=new ProviderManager(provider);
        return providerManager;
    }


    @Bean
    public AuthenticationEntryPoint unauthorizedHandler() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");

            Map<String, Object> result = new HashMap<>();
            result.put("code", 401);
            result.put("message", "未授权访问");
            result.put("timestamp", System.currentTimeMillis());

            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(result)
            );
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json;charset=UTF-8");

            Map<String, Object> result = new HashMap<>();
            result.put("code", 403);
            result.put("message", "权限不足");
            result.put("timestamp", System.currentTimeMillis());

            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(result)
            );
        };
    }


    /*
     * 在security安全框架中，提供了若干密码解析器实现类型。
     * 其中BCryptPasswordEncoder 叫强散列加密。可以保证相同的明文，多次加密后，
     * 密码有相同的散列数据，而不是相同的结果。
     * 匹配时，是基于相同的散列数据做的匹配。
     * Spring Security 推荐使用 BCryptPasswordEncoder 作为密码加密和解析器。
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}

