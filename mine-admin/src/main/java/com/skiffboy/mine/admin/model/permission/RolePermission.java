package com.skiffboy.mine.admin.model.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色权限
 */
@Data
public class RolePermission {
    @Schema(description = "菜单ID")
    private Long id;

    @Schema(description = "权限码")
    private String name;
}
