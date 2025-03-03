package com.weng.messaging.service;

import com.weng.messaging.model.entity.Passport;
import com.weng.messaging.model.entity.User;
import com.weng.messaging.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(String name, String email, String mobileNo, String password, User.Role role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setMobileNo(mobileNo);
        user.setRole(role);

        Passport emailPassport = createPassport(user, password, Passport.ClientType.EMAIL, email);
        Passport mobileNoPassport = createPassport(user, password, Passport.ClientType.MOBILE_NO, mobileNo);
        user.addPassport(Set.of(emailPassport, mobileNoPassport));
        return userRepo.save(user);
    }

    private Passport createPassport(User user, String password, Passport.ClientType clientType, String clientId) {
        Passport passport = new Passport();
        passport.setUser(user);
        passport.setClientType(clientType);
        passport.setClientId(clientId);
        passport.setPassword(passwordEncoder.encode(password));
        passport.setVerified(true);
        return passport;
    }

}
