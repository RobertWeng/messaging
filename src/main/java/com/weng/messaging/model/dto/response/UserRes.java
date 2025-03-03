package com.weng.messaging.model.dto.response;


import com.weng.messaging.model.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRes extends BaseRes {
    private String name;
    private String email;
    private String mobileNo;
    private User.AccountStatus accountStatus;
    private User.OnlineStatus onlineStatus;
}