package com.skiffboy.mine.admin.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skiffboy.mine.admin.model.settings.BackendSetting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInfoVo {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "个人签名")
    private String signed;

    @Schema(name = "backend_setting", description = "后台设置数据")
    @JsonProperty("backend_setting")
    private BackendSetting backendSetting;

    @Schema(description = "手机")
    private String phone;

    @Schema(description = "用户邮箱")
    private String email;

}
