package com.zfy.mp.service.login.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.zfy.mp.common.utils.JWTUtil;
import com.zfy.mp.domain.entity.LoginUser;
import com.zfy.mp.domain.vo.LoginUserVO;
import com.zfy.mp.service.login.LoginService;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * @文件名: LoginServiceImpl.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.login.impl
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-05 17:23
 * @版本号: V2.4.0
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Override
    public String login(LoginUserVO user) {
        UsernamePasswordAuthenticationToken usernamePassword  = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        // 实现登录逻辑，此时就回去调用 LoadUserByUsername 方法
        Authentication authenticate = authenticationManager.authenticate(usernamePassword);

        //  获取返回的用户信息
        Object principal = authenticate.getPrincipal();
        //强转为MySysUserDetails类型
        LoginUser userDetails = (LoginUser) principal;
        //返回token
        Map<String, Object> objectMap = BeanUtil.beanToMap(userDetails);
        return JWTUtil.createToken(JSONUtil.toJsonStr(userDetails), objectMap);
    }
}
