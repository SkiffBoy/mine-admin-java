package com.skiffboy.mine.admin.controller;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.skiffboy.mine.admin.controller.base.AbstractController;
import com.skiffboy.mine.admin.enums.common.Status;
import com.skiffboy.mine.admin.exception.BusinessException;
import com.skiffboy.mine.admin.mapper.permission.RoleMapper;
import com.skiffboy.mine.admin.model.entity.RoleEntity;
import com.skiffboy.mine.admin.model.entity.UserEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.admin.model.permission.Role;
import com.skiffboy.mine.admin.model.request.permission.PermissionRequest;
import com.skiffboy.mine.admin.model.response.Result;
import com.skiffboy.mine.admin.service.IMenuService;
import com.skiffboy.mine.admin.service.IUserService;
import com.skiffboy.mine.util.MenuUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Tag(name = "权限")
@RestController
@RequestMapping("/admin/permission")
public class PermissionController extends AbstractController {

    @Autowired
    private IUserService userService;
    @Resource
    private IMenuService menuService;
    @Resource
    private RoleMapper roleMapper;

    @Operation(
            operationId = "PermissionMenus",
            summary = "获取当前用户菜单",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @GetMapping(value = "/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<Menu> menus() {
        if (CurrentUser.isSuperAdmin()) {
            List<Menu> menusAll = menuService.listAll();
            menusAll = menusAll.stream()
                    .filter(menu -> menu.getStatus() == Status.Normal.getCode())
                    .collect(Collectors.toList());

            List<Menu> topMenuTree = MenuUtil.toTopTree(menusAll);
            return success(topMenuTree);
        } else {
            List<Menu> userMenus = userService.getUserMenus(CurrentUser.getId());
            return success(MenuUtil.toTopTree(userMenus));
        }
    }

    @Operation(
            operationId = "PermissionRoles",
            summary = "获取当前用户角色",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<List<Role>> roles() {
        if (CurrentUser.isSuperAdmin()) {
            List<Role> roles = roleMapper.selectListByQueryAs(
                    QueryWrapper.create().from(RoleEntity.class)
                            .eq(RoleEntity::getStatus, Status.Normal.getCode()), Role.class);
            return success(roles);
        } else {
            List<Role> userRoles = userService.getUserRoles(CurrentUser.getId());
            return success(userRoles);
        }
    }

    @Operation(
            operationId = "updateInfo",
            summary = "更新用户信息或密码",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result update(@RequestBody @Valid PermissionRequest request) {
        UserEntity entity = new UserEntity();
        UserEntity user = userService.getById(CurrentUser.getId());

        entity.setId(CurrentUser.getId());
        if (StrUtil.isNotEmpty(request.getNewPassword())) {
            if (!Objects.equals(request.getNewPassword(), request.getNewPasswordConfirmation())) {
                throw new BusinessException("user.new_password_confirmation_inconsistent");
            }
            if (!user.verifyPassword(request.getOldPassword())) {
                throw new BusinessException("user.old_password_error");
            }
            entity.setPassword(BCrypt.hashpw(request.getNewPassword()));
        }
        if (StrUtil.isNotEmpty(request.getNickname())) {
            entity.setNickname(request.getNickname());
        }
        if (request.getAvatar() != null) {
            entity.setAvatar(request.getAvatar());
        }
        if (request.getSigned() != null) {
            entity.setSigned(request.getSigned());
        }
        if (request.getBackendSetting() != null) {
            entity.setBackendSetting(request.getBackendSetting());
        }

        boolean success = userService.updateById(entity);
        return success ? success() : error(null);
    }

}