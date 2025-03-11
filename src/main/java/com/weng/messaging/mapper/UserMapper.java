package com.weng.messaging.mapper;

import com.weng.messaging.model.dto.response.LoginRes;
import com.weng.messaging.model.dto.response.UserRes;
import com.weng.messaging.model.entity.User;
import com.weng.messaging.security.JwtService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    @Lazy
    private JwtService jwtService;

    abstract UserRes toUserSimpleRes(User user);

    @Mapping(target = "accessToken", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    abstract LoginRes toLoginSimpleRes(User user);

    public UserRes toUserRes(User data) {
        return toUserSimpleRes(data);
    }

    public LoginRes toLoginRes(User data) {
        LoginRes res = toLoginSimpleRes(data);
        res.setAccessToken(jwtService.generateAccessToken(data));
        res.setRefreshToken(jwtService.generateRefreshToken(data));
        return res;
    }
}
