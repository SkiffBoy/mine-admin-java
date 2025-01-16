package com.skiffboy.mine.admin.model.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skiffboy.mine.admin.enums.common.Status;
import com.skiffboy.mine.admin.model.entity.UserEntity;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Schema(description = "创建用户")
@Data
@AutoMapper(target = UserEntity.class, reverseConvertGenerate = false)
public class UserRequest {

    @NotBlank(message = "{user.username}{jakarta.validation.constraints.NotBlank.message}")
    @Length(max = 20, message = "{user.username}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "{user.password}{jakarta.validation.constraints.NotBlank.message}")
    @Length(max = 32, message = "{user.password}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "密码")
    private String password;

    @NotNull(message = "{user.user_type}{jakarta.validation.constraints.NotNull.message}")
    @Schema(name = "user_type", description = "用户类型：(100系统用户)")
    @JsonProperty("user_type")
    private Integer userType;

    @NotBlank(message = "{user.nickname}{jakarta.validation.constraints.NotBlank.message}")
    @Length(max = 60, message = "{user.nickname}{org.hibernate.validator.constraints.Length.message}")
    @Pattern(regexp = "^[^\\s]+$")
    @Schema(description = "用户昵称")
    private String nickname;

    @Length(max = 12, message = "{user.phone}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "手机")
    private String phone;

    @Length(max = 60, message = "{user.email}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "用户邮箱")
    private String email;

    @Length(max = 255, message = "{user.avatar}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "用户头像")
    private String avatar;

    @Length(max = 255, message = "{user.signed}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "个人签名")
    private String signed;

    @Schema(description = "状态 (1正常 2停用)")
    private Status status;

    @Length(max = 255, message = "{user.remark}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "备注")
    private String remark;
}
