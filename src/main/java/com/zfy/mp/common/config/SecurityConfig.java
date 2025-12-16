package com.zfy.mp.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
        )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/login")  // 忽略特定路径的CSRF检查
                ).oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/home"));
        return http.build();
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        System.err.println("进入权限的获取方法");
//
//        List<GrantedAuthority> authorities = new ArrayList<>(); // 授权信息列表
//// 将角色名称添加到授权信息列表中
////        roles.forEach(role->
////                authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
////        // 将权限名称添加到授权信息列表中
////        permissions.forEach(permission->
////                authorities.add(new SimpleGrantedAuthority(permission))
////        );
//        return authorities; // 返回授权信息列表
//
//    }
}

