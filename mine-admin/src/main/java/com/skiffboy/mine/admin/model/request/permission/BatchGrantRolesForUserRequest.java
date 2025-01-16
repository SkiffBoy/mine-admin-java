package com.skiffboy.mine.admin.model.request.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "批量授权用户角色")
public class BatchGrantRolesForUserRequest {

    @NotEmpty(message = "{role_list}{jakarta.validation.constraints.NotEmpty.message}")
    @Schema(name = "role_codes", description = "角色代码")
    @JsonProperty("role_codes")
    private List<String> roleCodes;

}
