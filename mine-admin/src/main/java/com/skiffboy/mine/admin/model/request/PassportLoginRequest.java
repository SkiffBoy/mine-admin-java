package com.skiffboy.mine.admin.model.request;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.util.regex.Pattern;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "登录请求", description = "登录请求参数")
public class PassportLoginRequest {
    @NotBlank(message = "{user.username}{jakarta.validation.constraints.NotBlank.message}")
    @Schema(description = "用户名", requiredMode = REQUIRED, example = "admin")
    private String username;

    @NotBlank(message = "{user.password}{jakarta.validation.constraints.NotBlank.message}")
    @Schema(description = "密码", requiredMode = REQUIRED, example = "123456")
    private String password;

    public String os(HttpServletRequest servletRequest) {
        String userAgent = servletRequest.getHeader(HttpHeaders.USER_AGENT);
        if (userAgent == null || userAgent.isEmpty()) {
            return "Unknown";
        }
        if (Pattern.compile("win", Pattern.CASE_INSENSITIVE).matcher(userAgent).find()) {
            return "Windows";
        } else if (Pattern.compile("mac", Pattern.CASE_INSENSITIVE).matcher(userAgent).find()) {
            return "MAC";
        } else if (Pattern.compile("linux", Pattern.CASE_INSENSITIVE).matcher(userAgent).find()) {
            return "Linux";
        } else if (Pattern.compile("unix", Pattern.CASE_INSENSITIVE).matcher(userAgent).find()) {
            return "Unix";
        } else if (Pattern.compile("bsd", Pattern.CASE_INSENSITIVE).matcher(userAgent).find()) {
            return "BSD";
        } else {
            return "Other";
        }
    }

    public String browser(HttpServletRequest servletRequest) {
        String userAgent = servletRequest.getHeader("User-Agent");
        return StrUtil.isEmpty(userAgent) ? "unknown" : userAgent;
    }

}
