package com.zfy.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.zfy.mp.common.utils.JWTUtil;
import com.zfy.mp.vo.user.LoginUserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/login")
    public String login(@RequestBody LoginUserVO loginUserVO) {
        Map<String, Object> objectMap = BeanUtil.beanToMap(loginUserVO);
        String token = JWTUtil.createJWT(JSONUtil.toJsonStr(loginUserVO), objectMap);
        return token;
    }


    @GetMapping("/getTokenInfo")
    public String getTokenInfo(@RequestParam String token) {
        try {
            Claims claims = JWTUtil.parseJWT(token);
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
