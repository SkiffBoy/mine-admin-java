package com.skiffboy.mine.admin.controller.permission;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.paginate.Page;
import com.skiffboy.mine.admin.controller.base.AbstractController;
import com.skiffboy.mine.admin.model.entity.UserEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.permission.RoleCode;
import com.skiffboy.mine.admin.model.permission.User;
import com.skiffboy.mine.admin.model.request.permission.BatchGrantRolesForUserRequest;
import com.skiffboy.mine.admin.model.request.user.ResetPasswordRequest;
import com.skiffboy.mine.admin.model.request.user.UserRequest;
import com.skiffboy.mine.admin.model.request.user.UserRequest;
import com.skiffboy.mine.admin.model.response.PageResponse;
import com.skiffboy.mine.admin.model.response.Result;
import com.skiffboy.mine.admin.service.IUserService;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户信息表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/admin/user")
public class UserController extends AbstractController {

    @Resource
    private IUserService userService;
    @Resource
    private Converter converter;

    @Operation(
            operationId = "userList",
            summary = "用户列表",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:user:index", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<PageResponse<User>> pageList(@RequestParam(required = false) Map<String, Object> params) {
        Page<UserEntity> pageInfo = userService.page(params);
        List<User> list = converter.convert(pageInfo.getRecords(), User.class);
        return success(new PageResponse(pageInfo.getTotalRow(), list));
    }

    @Operation(
            operationId = "userCreate",
            summary = "创建用户",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:user:save", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Result create(@RequestBody @Valid UserRequest request) {
        userService.create(request);
        return success();
    }

    @Operation(
            operationId = "updateInfo",
            summary = "更新用户信息",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:user:update", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Result updateInfo(@RequestBody @Valid UserRequest request) {
        // 修改个人信息
        userService.updateById(CurrentUser.getId(), request);
        return success();
    }

    @Operation(
            operationId = "updatePassword",
            summary = "重置密码",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:user:password", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @PutMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        Long id = request.getId();
        boolean success = userService.resetPassword(id);
        return success ? success() : error(null);
    }

    @Operation(
            operationId = "userUpdate",
            summary = "更新用户",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:user:update", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result save(@PathVariable("userId") Long userId,
                       @RequestBody @Valid UserRequest request) {
        boolean success = userService.updateById(userId, request);
        return success ? success() : error(null);
    }

    @Operation(
            operationId = "userDelete",
            summary = "删除用户",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:user:delete", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Result delete(@RequestBody List<Long> ids) {
        userService.deleteById(ids);
        return success();
    }

    @Operation(
            operationId = "getUserRole",
            summary = "获取用户角色列表",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:user:getRole", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @GetMapping(value = "/{userId}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getUserRole(@PathVariable("userId") Long userId) {
        List<RoleCode> roles = userService.getUserRoleCodes(userId);
        return success(roles);
    }

    @Operation(
            operationId = "batchGrantRolesForUser",
            summary = "批量授权用户角色",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:user:setRole", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @PutMapping(value = "/{userId}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result batchGrantRolesForUser(@PathVariable("userId") Long userId,
                                         @RequestBody @Valid BatchGrantRolesForUserRequest request) {
        List<String> roleCodes = request.getRoleCodes();
        userService.batchGrantRoleForUser(userId, roleCodes);
        return success();
    }

}