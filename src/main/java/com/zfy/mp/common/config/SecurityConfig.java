package com.zfy.mp.common.config;

import com.zfy.mp.service.user.SysUserDetailsService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


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
public class SecurityConfig {
    @Resource
    private SysUserDetailsService sysUserDetailsService;

/**
 * 配置Spring Security的安全过滤器链
 * @param http HttpSecurity对象，用于配置安全设置
 * @return 配置好的SecurityFilterChain实例
 * @throws Exception 可能抛出的异常
 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // 配置HTTP请求授权规则
        http.authorizeHttpRequests(authorize ->
            // 允许所有路径的匿名访问
                authorize.requestMatchers("/**").permitAll()
                    // 允许特定路径（登录和GitHub回调）的匿名访问
                        .requestMatchers("/login", "oauth2/**","lbl/oauth2/github/callback").permitAll()
                    // 所有其他请求都需要身份验证
                        .anyRequest().authenticated()
        )
            // 配置CSRF防护
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/login",
                                "/oauth2/**",
                                "/lbl/oauth2/github/**",
                                "/oauth2/github/callback")  // 忽略特定路径的CSRF检查
                ).oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                                .redirectionEndpoint(redirection ->
                                redirection
                                        .baseUri("/lbl/oauth2/github/callback"))
                        );
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

