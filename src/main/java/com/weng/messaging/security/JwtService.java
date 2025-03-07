package com.weng.messaging.security;

import com.weng.messaging.config.JwtConfig;
import com.weng.messaging.model.entity.User;
import com.weng.messaging.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.time.DateUtils.addMinutes;

@Service
@Slf4j
public class JwtService {

    private final static String ISSUER = "WENG_MESSAGING_SERVICE";
    private final static String CLAIM_USAGE = "usage";
    private final static String USAGE_ACCESS_TOKEN = "access_token";
    private final static String USAGE_REFRESH_TOKEN = "access_token";

    @Autowired
    private UserService userService;

    @Autowired
    private JwtConfig jwtConfig;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    public User verifyAccessToken(String token) {
        return verifyToken(token, USAGE_ACCESS_TOKEN);
    }

    public User verifyRefreshToken(String token) {
        return verifyToken(token, USAGE_REFRESH_TOKEN);
    }

    // Use to verify access token by passing in token
    private User verifyToken(String token, String usage) {
        if (isBlank(token))
            return null;
        try {
            String userId = Jwts.parser()
                    .requireIssuer(ISSUER)
                    .require(CLAIM_USAGE, usage)
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return userService.findById(Long.parseLong(userId));
        } catch (ExpiredJwtException e) {
            log.info("Token expired: {}", e.getMessage());
        } catch (Exception e) {
            log.info("Invalid token: {}", e.getMessage());
        }
        return null;
    }

    // Use to generate access token (JWT token) when user login or registered
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .issuer(ISSUER)
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date())
                .expiration(addMinutes(new Date(), jwtConfig.getAccessTokenExpInMinute()))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .claim(CLAIM_USAGE, USAGE_ACCESS_TOKEN)
                .compact();
    }

    // Use to generate refresh token (JWT token) when access token expired
    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .issuer(ISSUER)
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date())
                .expiration(addMinutes(new Date(), jwtConfig.getRefreshTokenExpInMinute()))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .claim(CLAIM_USAGE, USAGE_REFRESH_TOKEN)
                .compact();
    }
}