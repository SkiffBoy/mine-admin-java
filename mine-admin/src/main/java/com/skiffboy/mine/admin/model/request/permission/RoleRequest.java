package com.skiffboy.mine.admin.model.request.permission;

import com.skiffboy.mine.admin.enums.common.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class RoleRequest {

    @NotBlank(message = "{role.name}{jakarta.validation.constraints.NotBlank.message}")
    @Length(max = 60, message = "{role.name}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "角色名称")
    private String name;

    @NotBlank(message = "{role.code}{jakarta.validation.constraints.NotBlank.message}")
    @Length(max = 60, message = "{role.code}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "角色代码")
    private String code;

    @Schema(description = "状态 (1正常 2停用)")
    private Status status;

    @NotNull(message = "{role.sort}{jakarta.validation.constraints.NotNull.message}")
    @Schema(description = "排序")
    private int sort;

    @Length(max = 255, message = "{role.remark}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "备注")
    private String remark;

}
