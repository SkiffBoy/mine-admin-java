package com.skiffboy.mine.admin.model.permission;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skiffboy.mine.admin.model.settings.BackendSetting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @Schema(description = "用户ID，主键")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @JsonIgnore
    @Schema(description = "密码", hidden = true)
    private String password;

    @Schema(name = "user_type", description = "用户类型:100=系统用户")
    @JsonProperty("user_type")
    private Integer userType;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "手机")
    private String phone;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "个人签名")
    private String signed;

    @Schema(description = "状态:1=正常,2=停用")
    private Integer status;

    @Schema(name = "login_ip", description = "最后登陆IP")
    @JsonProperty("login_ip")
    private String loginIp;

    @Schema(name = "login_time", description = "最后登陆时间")
    @JsonProperty("login_time")
    private Date loginTime;

    @Schema(name = "backend_setting", description = "后台设置数据")
    @JsonProperty("backend_setting")
    private BackendSetting backendSetting;

    @Schema(name = "created_by", description = "创建者")
    @JsonProperty("created_by")
    private Long createdBy;

    @Schema(name = "updated_by", description = "更新者")
    @JsonProperty("updated_by")
    private Long updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "created_at", description = "创建时间")
    @JsonProperty("created_at")
    public Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "updated_at", description = "更新时间")
    @JsonProperty("updated_at")
    public Date updatedAt;

    @Schema(description = "备注")
    private String remark;

}
