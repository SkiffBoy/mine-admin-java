package com.skiffboy.mine.admin.model.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Menu {
    @Schema(description = "主键")
    private Long id;

    @Schema(name = "parent_id", description = "父ID")
    @JsonProperty("parent_id")
    private Long parentId;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "跳转地址")
    private String redirect;

    @Schema(description = "地址")
    private String path;

    @Schema(description = "状态 (1正常 2停用)")
    private Integer status;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    @Schema(description = "附加属性")
    private Meta meta;

    @Schema(description = "排序")
    private int sort;

    @Schema(name = "created_by", description = "创建者")
    private int createdBy;

    @Schema(name = "updated_by", description = "更新者")
    private int updatedBy;

    @Schema(name = "created_at", description = "创建时间")
    @JsonProperty("created_at")
    public Date createdAt;

    @Schema(name = "updated_at", description = "更新时间")
    @JsonProperty("updated_at")
    public Date updatedAt;

    @Schema(description = "备注")
    private String remark;

    private List<Menu> children;

    public void addChild(Menu menu) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(menu);
    }
}
