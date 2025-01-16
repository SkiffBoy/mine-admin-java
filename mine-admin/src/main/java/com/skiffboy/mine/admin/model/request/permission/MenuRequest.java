package com.skiffboy.mine.admin.model.request.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skiffboy.mine.admin.enums.common.Status;
import com.skiffboy.mine.admin.model.permission.Meta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuRequest {
    @Schema(description = "主键")
    private Long id;

    @Schema(name = "parent_id", description = "父ID")
    @JsonProperty("parent_id")
    private Long parentId;

    @NotBlank(message = "{menu.name}{jakarta.validation.constraints.NotBlank.message}")
    @Length(max = 255, message = "{menu.name}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "菜单名称")
    private String name;

    @Length(max = 255, message = "{menu.path}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "地址")
    private String path;

    @Schema(description = "附加属性")
    private Meta meta;

    @Length(max = 255, message = "{menu.component}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "组件路径")
    private String component;

    @Length(max = 255, message = "{menu.redirect}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "跳转地址")
    private String redirect;

    @Schema(description = "状态 (1正常 2停用)")
    private Status status;

    @Schema(description = "排序")
    private Integer sort;

    @Length(max = 255, message = "{menu.remark}{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "备注")
    private String remark;

    @Schema(name = "is_hidden", description = "是否隐藏")
    @JsonProperty("is_hidden")
    private String isHidden;

    private List<MenuRequest> btnPermission = new ArrayList<>();

}
