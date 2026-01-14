package com.zfy.mp.service.login.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zfy.mp.common.constants.Const;
import com.zfy.mp.common.constants.RedisConst;
import com.zfy.mp.common.utils.IpUtils;
import com.zfy.mp.common.utils.RedisCache;
import com.zfy.mp.domain.entity.SysUser;
import com.zfy.mp.domain.request.oauth.GithubBody;
import com.zfy.mp.domain.vo.LoginUserVO;
import com.zfy.mp.mapper.UserMapper;
import com.zfy.mp.service.login.LoginService;
import com.zfy.mp.service.login.OauthLoginService;
import com.zfy.mp.service.user.SysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

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
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserMapper userMapper;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private LoginService loginService;
    @Resource
    private RedisCache redisCache;

    @Override
    public String handleLogin(HttpServletRequest request,String code, String state, Integer type) {
        AuthRequest authRequest = getGithubAuthRequest();

        // 获取当前请求的 HttpSession
//        HttpSession session = request.getSession();
//
//        // 手动将 state 放入 session 中，键名格式为 "JustAuth::STATE::{source}"
//        // GITHUB 是 source，对应你配置中的 AuthSource.GITHUB.getName()
//        String cacheKey = "JustAuth::STATE::GITHUB";
//        session.setAttribute(cacheKey, state);

        AuthCallback callback = new AuthCallback();
        callback.setCode(code);
        callback.setState(state);
        authRequest.authorize(state);
        AuthResponse response = authRequest.login(callback);
        AuthUser authUser = (AuthUser) response.getData();
//        callback.setState();
        // 第三方登录默认密码
        String enPassword = passwordEncoder.encode(authUser.getToken().getAccessToken());
        if (userMapper.selectCount(new LambdaQueryWrapper<SysUser>().eq(SysUser::getId, authUser.getUuid()).eq(SysUser::getRegisterType, type)) == 0) {
            String ipAddr = IpUtils.getIpAddr(request);
            SysUser user = SysUser.builder()
                    .id(authUser.getUuid())
                    .username(authUser.getUsername())
                    .avatar(authUser.getAvatar())
                    .nickname(authUser.getNickname())
                    .email(authUser.getEmail())
                    .password(enPassword)
                    .registerType(type)
                    .loginIp(ipAddr)
                    .loginTime(new Date())
                    .build();
            if (sysUserService.save(user)) {
                // todo
//                ipService.refreshIpDetailAsyncByUidAndRegister(user.getId());
            }
        }
        SysUser user = SysUser.builder().id(authUser.getUuid()).password(enPassword).build();
        userMapper.updateById(user);
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setUsername(user.getUsername());
        loginUserVO.setPassword(enPassword);

        redisCache.setCacheObject(RedisConst.REGISTER.concat(RedisConst.SEPARATOR).concat(Const.FRONTEND_LOGIN_TYPE), type);
        return loginService.login(loginUserVO);
//        switch (type) {
//            case 1:
//                return "?login_type=gitee&access_token=" + authUser.getToken().getAccessToken() + "&user_name=" + authUser.getUsername();
//            case 2:
//                return "?login_type=github&access_token=" + authUser.getToken().getAccessToken() + "&user_name=" + authUser.getUsername();
//        }

    }

    private AuthRequest getGithubAuthRequest() {
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId(githubBody.getClientId())
                .clientSecret(githubBody.getClientSecret())
                .redirectUri(githubBody.getRedirectUri())
                .build());
    }
}
