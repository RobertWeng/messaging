package com.weng.messaging.security;

import com.weng.messaging.model.entity.User;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityAuditorAware implements AuditorAware<User> {

    @Autowired
    private SecurityService securityService;

    @Override
    @NonNull
    public Optional<User> getCurrentAuditor() {
        User user = securityService.findMeOrAnonymous();
        if (user != null) return Optional.of(user);
        return Optional.empty();
    }
}
