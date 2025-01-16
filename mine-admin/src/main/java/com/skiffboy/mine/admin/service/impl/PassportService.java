package com.skiffboy.mine.admin.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.skiffboy.mine.admin.enums.common.JwtConstant;
import com.skiffboy.mine.admin.enums.user.UserType;
import com.skiffboy.mine.admin.exception.BusinessException;
import com.skiffboy.mine.admin.mapper.permission.UserMapper;
import com.skiffboy.mine.admin.model.entity.UserEntity;
import com.skiffboy.mine.admin.model.entity.UserLoginLogEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.vo.LoginTokenVo;
import com.skiffboy.mine.admin.service.IPassportService;
import com.skiffboy.mine.admin.service.IUserLoginLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PassportService implements IPassportService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private IUserLoginLogService userLoginLogService;

    private static final String KEY_PASSWORD_ERROR_TIMES = "password_error_count";

    @Override
    public LoginTokenVo login(String username, String password, UserType userType, String ip, String browser, String os) {
        if (userType == null) {
            userType = UserType.SYSTEM;
        }
        if (ip == null) {
            ip = "0.0.0.0";
        }
        if (browser == null) {
            browser = "unknown";
        }
        if (os == null) {
            os = "unknown";
        }

        final UserLoginLogEntity loginLog = new UserLoginLogEntity();
        try {
            loginLog.setUsername(username);
            loginLog.setIp(ip);
            loginLog.setOs(os);
            loginLog.setBrowser(browser);
            loginLog.setLoginTime(new Date());

            UserEntity user = userMapper.findByUnameType(username, userType);
            if (user == null) {
                throw new BusinessException("auth.validation_failed");
            }
            StpUtil.checkDisable(user.getId());
            SaSession session = SaSessionCustomUtil.getSessionById(KEY_PASSWORD_ERROR_TIMES + "@" + user.getId());
            if (!user.verifyPassword(password)) {
                int passwordErrorCount = session.get(KEY_PASSWORD_ERROR_TIMES, 0) + 1;
                session.set(KEY_PASSWORD_ERROR_TIMES, passwordErrorCount);
                if (passwordErrorCount >= 5) {
                    // 封禁五分钟后自动解禁
                    StpUtil.disable(user.getId(), 60 * 5);
                }

                throw new BusinessException("auth.password_error");
            }
            session.delete("password_error_count");
            loginLog.setStatus(true);
            StpUtil.login(user.getId(), SaLoginModel.create()
                    .setExtra(JwtConstant.USERNAME, username));

            return CurrentUser.getLoginToken();
        } catch (BusinessException e) {
            loginLog.setStatus(false);
            loginLog.setMessage(e.getResponse().getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            loginLog.setStatus(false);
            loginLog.setMessage(e.getMessage());
            throw new BusinessException(e.getMessage());
        } finally {
            // 记录日志
            userLoginLogService.save(loginLog);
        }
    }

}
