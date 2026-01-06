package com.zfy.mp.common.utils;

import com.zfy.mp.common.constants.RedisConst;
import com.zfy.mp.domain.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 
 *
 * @文件名: JWTUtil.java
 * @工程名: mp
 * @包名: com.zfy.mp.utils
 * @描述: jwt
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-15 14:11
 * @版本号: V2.4.0
 */
@Component
public class JWTUtil {
    @Resource
    private RedisCache redisCache;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;
    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;
    // jwt——key为服务器私钥
    private  final String JWT_KEY = "VGhpc0lzQVNlY3VyZUtleVdpdGhNb3JlVGhhbjI1NkJpdHM";

    /**
     * 创建jwt
     * @return
     */
    public  String createToken(LoginUser loginUser) {


        String uuid = getUUID();
        loginUser.setToken(uuid);
        refreshToken(loginUser);
        Map<String, Object> claims = new HashMap<>();
        claims.put(RedisConst.LOGIN_USER_KEY, uuid);
        return createToken(uuid, claims);
    }

    private  String createToken(String uuid, Map<String, Object> claims) {
        Date expireDate = getExpireDate();

        String token = Jwts.builder()
                .setId(uuid)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, generalKey())
                .setExpiration(expireDate).compact();
        return token;
    }

    private  Date getExpireDate() {
        long nowMillis = System.currentTimeMillis();
        long expMills = nowMillis + expireTime * MILLIS_MINUTE;
        Date expDate = new Date(expMills);
        return expDate;
    }

    /**
     * 校验JWT
     */
    public  Claims parseToken(String token) throws  Exception{
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private  SecretKey generalKey() {

        if (JWT_KEY == null || JWT_KEY.isEmpty()) {
            // 使用JWT库提供的方法生成符合安全要求的密钥
            return Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }

        try {
            byte[] encodeKey = Base64.getUrlDecoder().decode(JWT_KEY);
            // 确保密钥长度符合要求
            if (encodeKey.length < 32) {
                throw new IllegalArgumentException("密钥长度不足256位");
            }
            return new SecretKeySpec(encodeKey, 0, encodeKey.length, "HmacSHA256");
        } catch (Exception e) {
            // fallback到安全的密钥生成方法
            return Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
    }

    private  String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }
    public  void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
        redisCache.setCacheObject(getTokenKey(loginUser.getToken()), loginUser, expireTime, TimeUnit.MINUTES);
    }

    public  String getTokenKey(String uuid) {
        return RedisConst.LOGIN_TOKEN_KEY + uuid;
    }
}
