package com.weng.messaging.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class JwtConfig {

    private final String secret;
    private final int accessTokenExpInMinute;
    private final int refreshTokenExpInMinute;

    public JwtConfig(
            @Value("${jwt.token.secret}") String secret,
            @Value("${jwt.token.accessTokenExpInMinute}") int accessTokenExpInMinute,
            @Value("${jwt.token.refreshTokenExpInMinute}") int refreshTokenExpInMinute) {
        this.secret = secret;
        this.accessTokenExpInMinute = accessTokenExpInMinute;
        this.refreshTokenExpInMinute = refreshTokenExpInMinute;
    }
}
