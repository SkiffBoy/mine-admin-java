package com.skiffboy.mine.admin.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skiffboy.mine.util.RequestIpUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(title = "UserLoginLog")
public class UserLoginLog implements Serializable {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "登录IP地址")
    private String ip;

    @Schema(name = "ip_location", description = "IP所属地")
    @JsonProperty(value = "ip_location")
    public String ipLocation;

    public String getIpLocation() {
        return RequestIpUtil.getRegion(ip);
    }

    @Schema(description = "操作系统")
    private String os;

    @Schema(description = "浏览器")
    private String browser;

    @Schema(description = "登录状态 (1成功 2失败)")
    private Integer status;

    @Schema(description = "提示消息")
    private String message;

    @Schema(name = "login_time", description = "登录时间")
    @JsonProperty(value = "login_time")
    private Date loginTime;

    @Schema(description = "备注")
    private String remark;
}
