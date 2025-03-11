package com.weng.messaging.service;

import com.weng.messaging.exception.Catch;
import com.weng.messaging.exception.Error;
import com.weng.messaging.mapper.UserMapper;
import com.weng.messaging.model.dto.response.UserRes;
import com.weng.messaging.model.entity.Passport;
import com.weng.messaging.model.entity.User;
import com.weng.messaging.repo.UserRepo;
import com.weng.messaging.security.SecurityService;
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

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserMapper userMapper;

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> Catch.invalidRequest(Error.Msg.INVALID_CREDENTIAL));
    }

    public UserRes findMe() {
        User user = securityService.findMe();
        return userMapper.toUserRes(user);
    }

    public User createUser(String name, String email, String mobileNo, String password, User.Role role) {
        verifyBeforeInsert(email, mobileNo);
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

    private void verifyBeforeInsert(String email, String mobileNo) {
        if (userRepo.existsByEmail(email)) throw Catch.invalidRequest(Error.Msg.USER_EXISTED);
        if (userRepo.existsByMobileNo(mobileNo)) throw Catch.invalidRequest(Error.Msg.USER_EXISTED);
    }

}
