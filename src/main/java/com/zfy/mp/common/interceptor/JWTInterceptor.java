package com.zfy.mp.common.interceptor;

import com.zfy.mp.common.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 
 *
 * @文件名: JWTInterceptor.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.interceptor
 * @描述: 拦截器
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-15 14:23
 * @版本号: V2.4.0
 */
public class JWTInterceptor implements HandlerInterceptor {
    public static final Logger logger = LoggerFactory.getLogger(JWTInterceptor.class);

    /**
     * 添加用户身份认证
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//    String header = request.getHeader("Authorization");
//    if (header == null || header.isEmpty()) {
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), "未找到认证信息");
//        return false;
//    }
//
//    // 检查是否以 "Bearer " 开头
//    if (!header.toLowerCase().startsWith("bearer ")) {
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), "认证格式错误");
//        return false;
//    }
//
//    // 提取实际的 token（去掉 "Bearer " 前缀）
//    String token = header.substring(7);
//    if (token.isEmpty()) {
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), "认证信息为空");
//        return false;
//    }
//
//    try {
//        Claims claims = JWTUtil.parseJWT(token);
//        if (claims == null || JWTUtil.isTokenExpired(token)) {
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), "token已失效，请重新登录");
//            return false;
//        }
//
//        // 刷新ttl
//        JWTUtil.refreshToken(token);
//        return true;
//
//    } catch (JwtException e) {
//        logger.error("token解析异常：{}", e.getMessage());
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), "无效的token");
//        return false;
//    }

        String header = request.getHeader("Authorization");
        if (header == null || header.isEmpty()) {
            return false;
        }

        String token = "";
        if (header.toLowerCase().startsWith("bearer ")) {
            token = header.substring(7);
            if (token.isEmpty())
                return false;
            try {
                Claims claims = JWTUtil.parseJWT(token);
                if (claims == null || JWTUtil.isTokenExpired(token)) {
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), token + "失效，请重新登录");
                    return false;
                }
            } catch (JwtException  e) {
                logger.info("token解析异常： {}", e.getMessage());
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return false;
            }

            // 刷新ttl
            JWTUtil.refreshToken(token);
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "token格式错误");
            return false;
        }
        return true;

    }
}
