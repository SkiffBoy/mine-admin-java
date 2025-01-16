package com.skiffboy.mine.admin.model.request.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skiffboy.mine.admin.model.settings.BackendSetting;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class PermissionRequest {

    @Length(max = 255, message = "{user.nickname}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "用户昵称")
    private String nickname;

    @Length(min = 8, message = "{user.new_password}{org.hibernate.validator.constraints.Length.message}")
    @Schema(name = "new_password", description = "新密码")
    @JsonProperty("new_password")
    private String newPassword;

    @Length(min = 8, message = "{user.password_confirmation}{org.hibernate.validator.constraints.Length.message}")
    @Schema(name = "new_password_confirmation", description = "新密码确认")
    @JsonProperty("new_password_confirmation")
    private String newPasswordConfirmation;

    @Schema(name = "old_password", description = "旧密码")
    @JsonProperty("old_password")
    private String oldPassword;

    @Length(max = 255, message = "{user.avatar}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "用户头像")
    private String avatar;

    @Length(max = 255, message = "{user.signed}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "个人签名")
    private String signed;

    @Schema(name = "backend_setting", description = "后台设置数据")
    @JsonProperty("backend_setting")
    private BackendSetting backendSetting;

}
