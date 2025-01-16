package com.skiffboy.mine.admin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.skiffboy.mine.admin.controller.base.AbstractController;
import com.skiffboy.mine.admin.enums.user.UserType;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.request.PassportLoginRequest;
import com.skiffboy.mine.admin.model.response.Result;
import com.skiffboy.mine.admin.model.vo.LoginTokenVo;
import com.skiffboy.mine.admin.model.vo.UserInfoVo;
import com.skiffboy.mine.admin.service.IPassportService;
import com.skiffboy.mine.admin.service.IUserService;
import com.skiffboy.mine.util.RequestIpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "admin:passport")
@RestController
@RequestMapping("/admin/passport")
public class PassportController extends AbstractController {
    @Resource
    private IPassportService passportService;
    @Resource
    private IUserService userService;

    @Operation(
            operationId = "passportLogin",
            summary = "系统登录"
    )
    @ApiResponse(responseCode = "200")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<LoginTokenVo> login(@RequestBody PassportLoginRequest request, HttpServletRequest servletRequest) {
        String username = request.getUsername();
        String password = request.getPassword();

        String os = request.os(servletRequest);
        String browser = request.browser(servletRequest);
        String ip = RequestIpUtil.getIpv4Address(servletRequest);

        return success(
                passportService.login(
                        username,
                        password,
                        UserType.SYSTEM,
                        ip,
                        browser,
                        os
                )
        );
    }

    @Operation(
            operationId = "passportLogout",
            summary = "退出",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result logout() {
        if (CurrentUser.isLogin()) {
            CurrentUser.logout();
        }
        return success();
    }

    @Operation(
            operationId = "getInfo",
            summary = "获取用户信息",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @GetMapping(value = "/getInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<UserInfoVo> getInfo() {
        UserInfoVo vo = userService.getInfo(CurrentUser.getId());
        return success(vo);
    }

    @Operation(
            operationId = "refresh",
            summary = "刷新token",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckLogin
    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<LoginTokenVo> refresh() {
        // 手动续签 token
        StpUtil.updateLastActiveToNow();
        return success(CurrentUser.getLoginToken());
    }

}
