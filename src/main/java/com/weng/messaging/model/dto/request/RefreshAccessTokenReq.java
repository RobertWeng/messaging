package com.weng.messaging.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshAccessTokenReq {

    @NotBlank
    private String refreshToken;
}
