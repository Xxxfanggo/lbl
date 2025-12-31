package com.zfy.mp.service.user.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zfy.mp.common.utils.JWTUtil;
import com.zfy.mp.domain.entity.SysUser;
import com.zfy.mp.mapper.SysUserMapper;
import com.zfy.mp.service.user.SysUserDetails;
import com.zfy.mp.service.user.SysUserService;
import com.zfy.mp.domain.vo.user.LoginUserVO;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * @文件名: SysUserServiceImpl.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.user.impl
 * @描述: todo
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-19 15:28
 * @版本号: V2.4.0
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService  {
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
        SysUserDetails userDetails = (SysUserDetails) principal;
        //返回token
        Map<String, Object> objectMap = BeanUtil.beanToMap(userDetails);
        return JWTUtil.createToken(JSONUtil.toJsonStr(userDetails), objectMap);
    }
}
