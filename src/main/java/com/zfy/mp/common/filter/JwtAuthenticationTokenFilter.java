package com.zfy.mp.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zfy.mp.common.constants.RedisConst;
import com.zfy.mp.common.utils.JWTUtil;
import com.zfy.mp.common.utils.RedisCache;
import com.zfy.mp.domain.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private JWTUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {


            String token = getTokenFromRequest(request);

            if (!StringUtils.hasText(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 检查token是否在黑名单中
            if (isTokenBlacklisted(token)) {
                handleAuthenticationFailure(response, "认证令牌已失效");
            }

            // 解析token
            Claims claims = jwtUtil.parseToken(token);
            String uuid = claims.get(RedisConst.LOGIN_USER_KEY).toString();
            String userKey = jwtUtil.getTokenKey(uuid);
            LoginUser user = redisCache.getCaheObject(userKey);
            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                if (!jwtUtil.isTokenExpired(token)) {
                    /*** 创建认证对象 ***/
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
                    /*** 设置认证信息 ***/
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    jwtUtil.refreshToken(user);
                } else {
                    handleAuthenticationFailure(response, "认证令牌无效");
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            logger.error("JWT token已过期: {}", e.getMessage());
            handleAuthenticationFailure(response, "认证令牌已过期");
        } catch (UnsupportedJwtException e) {
            logger.error("不支持的JWT token: {}", e.getMessage());
            handleAuthenticationFailure(response, "不支持的认证令牌");
        } catch (MalformedJwtException e) {
            logger.error("JWT token格式错误: {}", e.getMessage());
            handleAuthenticationFailure(response, "认证令牌格式错误");
        } catch (SecurityException e) {
            logger.error("JWT token签名验证失败: {}", e.getMessage());
            handleAuthenticationFailure(response, "认证令牌验证失败");
        } catch (IllegalArgumentException e) {
            logger.error("JWT token非法参数: {}", e.getMessage());
            handleAuthenticationFailure(response, "认证令牌参数错误");
        } catch (Exception e) {
            logger.error("认证过程中发生错误: {}", e.getMessage());
            handleAuthenticationFailure(response, "认证处理失败");
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isPublicRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return
                requestURI.equals("/login") ||
                        requestURI.equals("/register");
    }

    private boolean isTokenBlacklisted(String token) {
//        return redisTemplate.hasKey("blacklist:" + token);
        return false;
    }

    private void refreshTokenIfNeeded(String token) {
        // 实现token刷新逻辑
    }

    private void handleAuthenticationFailure(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> result = new HashMap<>();
        result.put("code", 401);
        result.put("message", message);
        result.put("timestamp", System.currentTimeMillis());

        response.getWriter().write(
                new ObjectMapper().writeValueAsString(result)
        );
    }
}

