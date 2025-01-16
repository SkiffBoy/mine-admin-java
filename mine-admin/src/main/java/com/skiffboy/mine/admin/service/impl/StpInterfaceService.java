package com.skiffboy.mine.admin.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.convert.Convert;
import com.skiffboy.mine.admin.mapper.RoleBelongsMenuMapper;
import com.skiffboy.mine.admin.mapper.UserBelongsRoleMapper;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.admin.model.permission.Role;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StpInterfaceService implements StpInterface {
    @Resource
    private UserBelongsRoleMapper userBelongsRoleMapper;
    @Resource
    private RoleBelongsMenuMapper roleBelongsMenuMapper;

    public List<Role> getRoles(Object userId) {
        return userBelongsRoleMapper.selectRolesByUserId(Convert.toLong(userId));
    }

    public List<Menu> getMenus(Object userId) {
        return getRoles(userId).stream()
                .flatMap(role -> roleBelongsMenuMapper.selectMenusByRoleId(role.getId()).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<Menu> menus = getMenus(loginId);
        return menus.stream().map(Menu::getName).collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleList(Object userId, String loginType) {
        return getRoles(userId).stream()
                .map(role -> role.getCode())
                .collect(Collectors.toUnmodifiableList());
    }
}
