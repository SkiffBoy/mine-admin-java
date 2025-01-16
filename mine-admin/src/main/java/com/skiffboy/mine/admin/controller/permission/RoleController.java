package com.skiffboy.mine.admin.controller.permission;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.paginate.Page;
import com.skiffboy.mine.admin.controller.base.AbstractController;
import com.skiffboy.mine.admin.exception.BusinessException;
import com.skiffboy.mine.admin.model.entity.RoleEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.permission.Role;
import com.skiffboy.mine.admin.model.permission.RolePermission;
import com.skiffboy.mine.admin.model.request.permission.BatchGrantPermissionsForRoleRequest;
import com.skiffboy.mine.admin.model.request.permission.RoleRequest;
import com.skiffboy.mine.admin.model.response.PageResponse;
import com.skiffboy.mine.admin.model.response.Result;
import com.skiffboy.mine.admin.model.vo.UserLoginLog;
import com.skiffboy.mine.admin.service.IRoleService;
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
 * 角色信息表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/admin/role")
public class RoleController extends AbstractController {

    @Resource
    private IRoleService roleService;
    @Resource
    private Converter converter;

    @Operation(
            operationId = "roleList",
            summary = "角色列表",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:role:index", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<PageResponse<UserLoginLog>> page(@RequestParam(required = false) Map<String, Object> params) {
        Page<RoleEntity> pageInfo = roleService.page(params);
        List<Role> list = converter.convert(pageInfo.getRecords(), Role.class);
        return success(new PageResponse(pageInfo.getTotalRow(), list));
    }

    @Operation(
            operationId = "roleCreate",
            summary = "创建角色",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:role:save", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Result create(@RequestBody @Valid RoleRequest request) {
        boolean success = roleService.create(request);
        return success ? success() : error(null);
    }

    @Operation(
            operationId = "roleSave",
            summary = "保存角色",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:role:update", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result save(@PathVariable("id") Long id, @RequestBody @Valid RoleRequest request) {
        boolean success = roleService.updateById(id, request);
        return success ? success() : error(null);
    }

    @Operation(
            operationId = "roleDelete",
            summary = "删除角色",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:role:delete", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Result delete(@RequestBody List<Long> ids) {
        roleService.deleteById(ids);
        return success();
    }

    @Operation(
            operationId = "setRolePermission",
            summary = "获取角色权限列表",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:role:getMenu", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @GetMapping(value = "/{id}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getRolePermissionForRole(@PathVariable("id") Long id) {
        List<RolePermission> rolePermission = roleService.getRolePermission(id);
        return success(rolePermission);
    }

    @Operation(
            operationId = "roleGrantPermissions",
            summary = "赋予角色权限",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:role:setMenu", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @PutMapping(value = "/{id}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result batchGrantPermissionsForRole(@PathVariable("id") Long id,
                                               @RequestBody @Valid BatchGrantPermissionsForRoleRequest request) {
        boolean existRole = roleService.existsById(id);
        if (!existRole) {
            throw new BusinessException("role.not_found");
        }
        roleService.batchGrantPermissionsForRole(id, request.getPermissions());
        return success();
    }

}