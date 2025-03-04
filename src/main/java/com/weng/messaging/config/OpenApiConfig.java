package com.weng.messaging.config;

import com.weng.messaging.controller.app.AuthController;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi userApi() {
        String[] packagesToScan = {AuthController.class.getPackageName()};
        return GroupedOpenApi.builder().group("User")
                .packagesToScan(packagesToScan)
                .build();
    }
}
