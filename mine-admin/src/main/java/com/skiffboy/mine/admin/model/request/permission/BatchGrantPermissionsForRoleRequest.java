package com.skiffboy.mine.admin.model.request.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "批量授权角色权限")
public class BatchGrantPermissionsForRoleRequest {

    @Schema(description = "权限代码")
    private List<String> permissions;

}
