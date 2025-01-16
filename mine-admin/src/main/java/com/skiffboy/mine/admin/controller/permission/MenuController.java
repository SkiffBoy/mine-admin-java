package com.skiffboy.mine.admin.controller.permission;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.skiffboy.mine.admin.controller.base.AbstractController;
import com.skiffboy.mine.admin.model.entity.MenuEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.admin.model.request.permission.MenuRequest;
import com.skiffboy.mine.admin.model.response.Result;
import com.skiffboy.mine.admin.service.IMenuService;
import com.skiffboy.mine.util.MenuUtil;
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
 * 菜单信息表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping("/admin/menu")
public class MenuController extends AbstractController {

    @Resource
    private IMenuService menuService;
    @Resource
    private Converter converter;

    @Operation(
            operationId = "menuList",
            summary = "菜单列表",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:menu:index", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<List<MenuEntity>> pageList(@RequestParam(required = false) Map<String, Object> params) {
        List<MenuEntity> list = menuService.list(params);
        List<Menu> topTree = MenuUtil.toTopTree(converter.convert(list, Menu.class));
        return success(topTree);
    }

    @Operation(
            operationId = "menuCreate",
            summary = "创建菜单",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:menu:create", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Result create(@RequestBody @Valid MenuRequest request) {
        menuService.create(request);
        return success();
    }

    @Operation(
            operationId = "menuEdit",
            summary = "编辑菜单",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:menu:save", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result save(@PathVariable("id") Long id, @RequestBody @Valid MenuRequest request) {
        menuService.updateById(request);
        return success();
    }

    @Operation(
            operationId = "menuDelete",
            summary = "删除菜单",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "permission:menu:delete", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Result delete(@RequestParam Long id) {
        menuService.removeById(id);
        return success();
    }

}