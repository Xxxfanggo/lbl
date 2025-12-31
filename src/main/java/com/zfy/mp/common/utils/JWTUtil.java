package com.zfy.mp.common.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.zfy.mp.domain.vo.user.LoginUserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

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
public class JWTUtil {
    // 过期时间
    public static final long JWT_TTL = 1000 * 60 * 60 * 24;
    // jwt——key为服务器私钥
    private static final String JWT_KEY = "VGhpc0lzQVNlY3VyZUtleVdpdGhNb3JlVGhhbjI1NkJpdHM";

    public String createToken() {

        return "";
    }

    /**
     * 创建jwt
     * @param subject 用户唯一标识ID
     * @return
     */
    public static String createToken(String subject, Map<String, Object>  claims) {
        JwtBuilder jwtBuilder = getJwtBuilder(subject, claims,null, getUUID());
        return "Bearer " + jwtBuilder.compact();
    }

    /**
     * 校验JWT
     */
    public static Claims parseToken(String token) throws  Exception{
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private static JwtBuilder getJwtBuilder(String subject, Map<String, Object> claims, Long ttlMillis, String uuid) {
        // 自行选择加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }

        long expMills = nowMillis + ttlMillis;
        Date expDate = new Date(expMills);
        JwtBuilder jb = Jwts.builder()
                .setId(uuid)
                .setClaims(claims)
                .setIssuer("zfy")
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey)
                .setExpiration(expDate);

        return jb;
    }

    private static SecretKey generalKey() {

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

    private static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public static String refreshToken(String token) {
        SecretKey secretKey = generalKey();
        Claims body = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        String subject = body.getSubject();

        // 验证用户信息
        LoginUserVO user = BeanUtil.toBean(subject, LoginUserVO.class);
        if (ObjUtil.isEmpty(user)){
            return "";
        }

        return createToken(JSONUtil.toJsonStr(user), null );
    }
}
