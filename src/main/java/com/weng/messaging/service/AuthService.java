package com.weng.messaging.service;

import com.weng.messaging.exception.Catch;
import com.weng.messaging.exception.Error;
import com.weng.messaging.mapper.UserMapper;
import com.weng.messaging.model.dto.request.CreateUserReq;
import com.weng.messaging.model.dto.request.LoginReq;
import com.weng.messaging.model.dto.response.LoginRes;
import com.weng.messaging.model.entity.Passport;
import com.weng.messaging.model.entity.User;
import com.weng.messaging.repo.PassportRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PassportRepo passportRepo;

    @Autowired
    private UserService userService;

    public LoginRes register(CreateUserReq req) {
        User user = userService.createUser(req.getName(), req.getEmail(), req.getMobileNo(), req.getPassword(), User.Role.USER);
        return userMapper.toLoginRes(user);
    }

    public LoginRes login(LoginReq req) {
        User user = loginByClientTypeAndId(req.getClientType(), req.getClientId(), req.getPassword());
        return userMapper.toLoginRes(user);
    }

    private User loginByClientTypeAndId(Passport.ClientType clientType, String username, String password) {
        Passport passport = passportRepo.findByClientTypeAndClientId(clientType, username).orElseThrow(() -> Catch.invalidRequest(Error.Msg.INVALID_CREDENTIAL));
        User user = passport.getUser();
        verifyUser(user);
        if (!passwordEncoder.matches(password, passport.getPassword())) {
            throw Catch.invalidRequest(Error.Msg.INVALID_CREDENTIAL);
        }
        return user;
    }

    private void verifyUser(User user) {
        if (User.AccountStatus.BLOCKED == user.getAccountStatus())
            throw Catch.invalidRequest(Error.Msg.ACCOUNT_BLOCKED);
    }
}
