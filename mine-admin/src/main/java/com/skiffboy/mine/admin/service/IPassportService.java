package com.skiffboy.mine.admin.service;

import com.skiffboy.mine.admin.enums.user.UserType;
import com.skiffboy.mine.admin.model.vo.LoginTokenVo;

public interface IPassportService {
    LoginTokenVo login(String username, String password, UserType userType, String ip, String browser, String os);
}
