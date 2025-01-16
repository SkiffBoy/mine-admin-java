package com.skiffboy.mine.admin.model.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RoleCode {
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色代码")
    private String code;
}
