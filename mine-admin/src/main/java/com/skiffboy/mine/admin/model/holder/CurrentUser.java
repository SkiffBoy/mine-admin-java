package com.skiffboy.mine.admin.model.holder;

import cn.dev33.satoken.stp.StpUtil;
import com.skiffboy.mine.admin.enums.common.JwtConstant;
import com.skiffboy.mine.admin.model.vo.LoginTokenVo;
import com.skiffboy.mine.util.RequestIpUtil;
import com.skiffboy.mine.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;

public class CurrentUser {
    public static final String ROLE_CODE_SUPER_ADMIN = "SuperAdmin";

    public static long getId() {
        return StpUtil.getLoginIdAsLong();
    }

    public static boolean isSuperAdmin() {
        return StpUtil.getRoleList().stream().anyMatch(ROLE_CODE_SUPER_ADMIN::equalsIgnoreCase);
    }

    public static boolean isLogin() {
        return StpUtil.isLogin();
    }

    public static void logout() {
        StpUtil.logout();
    }
    public static LoginTokenVo getLoginToken() {
        return new LoginTokenVo(StpUtil.getTokenInfo());
    }

    public static String getUsername() {
        return (String) StpUtil.getExtra(JwtConstant.USERNAME);
    }

    public static String getIp() {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request == null) {
            return null;
        }
        return RequestIpUtil.getIpv4Address(request);
    }
}
