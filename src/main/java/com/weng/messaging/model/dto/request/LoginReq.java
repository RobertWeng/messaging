package com.weng.messaging.model.dto.request;

import com.weng.messaging.model.entity.Passport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginReq {

    @NotBlank
    @Schema(defaultValue = "robertlee1@mail.com")
    private String clientId;

    @NotBlank
    @Schema(defaultValue = "123Qwe!!")
    private String password;

    @Schema(defaultValue = "EMAIL")
    private Passport.ClientType clientType;
}
