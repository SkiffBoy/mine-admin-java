package com.skiffboy.mine.admin.model.vo;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录成功返回对象")
public class LoginTokenVo {
    @Schema(name = "access_token", description = "Access Token", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjIwOTQwNTYsIm5iZiI6MTcyMjA5NDAiwiZXhwIjoxNzIyMDk0MzU2fQ.7EKiNHb_ZeLJ1NArDpmK6sdlP7NsDecsTKLSZn_3D7k")
    @JsonProperty(value = "access_token")
    private String accessToken;

    @Schema(name = "refresh_token", description = "Refresh Token", example = "eyJ0eXAiOi")
    @JsonProperty(value = "refresh_token")
    private String refreshToken = "";

    @Schema(name = "expire_at", description = "过期时间,单位秒", example = "300")
    @JsonProperty(value = "expire_at")
    private int expireAt;

    public LoginTokenVo(SaTokenInfo token) {
        this.setAccessToken(token.getTokenValue());
        // 当前登录者的 token 剩余有效时间 (单位: 秒)
        this.setExpireAt((int) token.getTokenTimeout());
    }

}
