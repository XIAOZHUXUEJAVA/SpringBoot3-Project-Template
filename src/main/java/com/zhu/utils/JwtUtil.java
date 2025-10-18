package com.zhu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类（适配 JJWT 0.12.x）
 */
public class JwtUtil {

    // 有效期为 24 小时
    public static final Long JWT_TTL = 24 * 60 * 60 * 1000L;
    // 设置秘钥明文（注意：生产环境应使用更强的密钥，至少32字节）
    public static final String JWT_KEY = "sangeng_secret_key_for_jwt_token_generation_32bytes";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成 JWT
     * @param subject token中要存放的数据（json格式）
     * @return JWT token
     */
    public static String createJWT(String subject) {
        return createJWT(getUUID(), subject, null);
    }

    /**
     * 生成 JWT
     * @param subject token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return JWT token
     */
    public static String createJWT(String subject, Long ttlMillis) {
        return createJWT(getUUID(), subject, ttlMillis);
    }

    /**
     * 创建 token
     * @param id JWT ID
     * @param subject 主题（可以是JSON数据）
     * @param ttlMillis 过期时间（毫秒）
     * @return JWT token
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        
        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        
        return Jwts.builder()
                .id(id)                    // 唯一的ID
                .subject(subject)          // 主题（可以是JSON数据）
                .issuer("sg")              // 签发者
                .issuedAt(now)             // 签发时间
                .expiration(expDate)       // 过期时间
                .signWith(secretKey)       // 使用密钥签名（自动使用HS256算法）
                .compact();
    }

    /**
     * 生成加密后的秘钥 secretKey
     * @return SecretKey
     */
    public static SecretKey generalKey() {
        // JJWT 0.12.x 推荐使用 Keys.hmacShaKeyFor() 生成密钥
        return Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解析 JWT
     * @param jwt JWT token
     * @return Claims
     * @throws Exception 解析异常
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .verifyWith(secretKey)     // 设置签名密钥
                .build()
                .parseSignedClaims(jwt)    // 解析 JWT
                .getPayload();             // 获取 payload（即 Claims）
    }

    public static void main(String[] args) throws Exception {
        // 测试生成和解析 JWT
        String token = createJWT("test_user_data");
        System.out.println("Generated Token: " + token);
        
        Claims claims = parseJWT(token);
        System.out.println("Parsed Claims: " + claims);
    }
}
