package com.zfy.mp.service.login.impl;

import com.zfy.mp.domain.request.oauth.GithubBody;
import com.zfy.mp.service.login.OauthLoginService;
import jakarta.annotation.Resource;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @文件名: OauthLoginServiceImpl.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.login.impl
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-06 17:22
 * @版本号: V2.4.0
 */
@Service
public class OauthLoginServiceImpl implements OauthLoginService {

    @Resource
    private GithubBody githubBody;

    @Override
    public String handleLogin(String code, String state, Integer type) {
        AuthRequest authRequest = getGithubAuthRequest();

        AuthCallback callback = new AuthCallback();
        callback.setCode(code);
        callback.setState(state);
        AuthResponse response = authRequest.login(callback);
        AuthUser authUser = (AuthUser) response.getData();
//        callback.setState();
        return "";
    }

    private AuthRequest getGithubAuthRequest() {
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId(githubBody.getClientId())
                .clientSecret(githubBody.getClientSecret())
                .redirectUri(githubBody.getRedirectUri())
                .build());
    }
}
