package com.weng.messaging.security;

import com.weng.messaging.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public User findMe() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return principal.getUser();
    }

    public User findMeOrAnonymous() {
        try {
            return findMe();
        } catch (Exception e) {
            return null;
        }
    }
}
