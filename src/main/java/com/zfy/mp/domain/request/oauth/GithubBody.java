package com.zfy.mp.domain.request.oauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 * @文件名: GithubBody.java
 * @工程名: mp
 * @包名: com.zfy.mp.domain.request.oauth
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-06 17:14
 * @版本号: V2.4.0
 */
@Data
@Component
@ConfigurationProperties(value = "spring.security.oauth2.client.registration.github")
public class GithubBody {
    private String clientId; //客户端id
    private String redirectUri; //登陆后后的回调地址
    private String clientSecret; //密钥
}
