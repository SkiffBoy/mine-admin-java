package com.skiffboy.mine.admin.model.permission;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色代码")
    private String code;

    @Schema(name = "data_scope", description = "数据范围（1：全部数据权限 2：自定义数据权限 3：本部门数据权限 4：本部门及以下数据权限 5：本人数据权限）")
    @JsonProperty("data_scope")
    private Integer dataScope;

    @Schema(description = "状态:1=正常,2=停用")
    private Integer status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(name = "created_by", description = "创建者")
    @JsonProperty("created_by")
    private Long createdBy;

    @Schema(name = "updated_by", description = "更新者")
    @JsonProperty("updated_by")
    private Long updatedBy;

    @Schema(name = "created_at", description = "创建时间")
    @JsonProperty("created_at")
    public Date createdAt;

    @Schema(name = "updated_at", description = "更新时间")
    @JsonProperty("updated_at")
    public Date updatedAt;

    @Schema(description = "备注")
    private String remark;

}
