package com.weng.messaging.security;

import com.weng.messaging.model.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.time.DateUtils.addMinutes;

@Service
@Slf4j
public class JwtService {

    private final static String ISSUER = "WENG_USER_SERVICE";
    private final static String CLAIM_USAGE = "usage";
    private final static String USAGE_ACCESS_TOKEN = "access_token";

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.accessTokenExpInMinute}")
    private int accessTokenExpInMinute;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Use to verify access token by passing in token
    public User verifyAccessToken(String token) {
        if (isBlank(token))
            return null;
        try {

            String userId = Jwts.parser()
                    .requireIssuer(ISSUER)
                    .require(CLAIM_USAGE, USAGE_ACCESS_TOKEN)
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

            // TODO: userId Validation
            return Long.parseLong(userId);
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
                .expiration(addMinutes(new Date(), accessTokenExpInMinute))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .claim(CLAIM_USAGE, USAGE_ACCESS_TOKEN)
                .compact();
    }
}