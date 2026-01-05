package com.zfy.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zfy.mp.common.utils.JWTUtil;
import com.zfy.mp.domain.entity.SysUser;
import com.zfy.mp.service.login.LoginService;
import com.zfy.mp.service.user.SysUserService;
import com.zfy.mp.domain.vo.LoginUserVO;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 
 *
 * @文件名: LoginController.java
 * @工程名: mp
 * @包名: com.zfy.mp.controller
 * @描述: login
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-15 13:38
 * @版本号: V2.4.0
 */
@RestController
public class LoginController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginUserVO loginUserVO) {
        return loginService.login(loginUserVO);
    }

    @PostMapping("/register")
    public String register(@RequestBody @Validated LoginUserVO loginUserVO) {
        return sysUserService.save(BeanUtil.copyProperties(loginUserVO, SysUser.class)) ? "注册成功" : "注册失败";
    }

    @GetMapping("/testFilter")
    public String testFilter() {
        // 添加调试信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("用户权限: " + auth.getAuthorities());
        System.out.println("是否有all权限: " + auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("basic")));
        return "testFilter successfully";
    }

    @GetMapping("/testHasAnyAuthority")
    @PreAuthorize("hasAnyAuthority('basic')")
    public String testHasAnyAuthority() {
        // 添加调试信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("当前用户: " + auth.getName());
        System.out.println("用户权限: " + auth.getAuthorities());
        System.out.println("是否有all权限: " + auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("basic")));
        return "testHasAnyAuthority successfully";
    }


    @GetMapping("/getTokenInfo")
    public String getTokenInfo(@RequestParam String token) {

        // 添加调试信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("当前用户: " + auth.getName());
        System.out.println("用户权限: " + auth.getAuthorities());
        System.out.println("是否有all权限: " + auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("all")));
        try {
            Claims claims = JWTUtil.parseToken(token);
            return "token信息： " + claims;
        } catch (Exception e) {
            return "token解析异常： " + e.getMessage();
        }
    }

    @GetMapping("/testSecurity")
    public String testSecurity() {
       return "test successfully";
    }
}
