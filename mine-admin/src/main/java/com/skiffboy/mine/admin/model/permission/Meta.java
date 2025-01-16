package com.skiffboy.mine.admin.model.permission;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skiffboy.mine.admin.enums.menu.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meta {

    @NotBlank(message = "{jakarta.validation.constraints.NotBlank.message}")
    @Length(max = 255, message = "{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "标题")
    private String title;

    @Length(max = 255, message = "{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "国际化")
    private String i18n;

    @Length(max = 255, message = "{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "徽章")
    private String badge;

    @Length(max = 255, message = "{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "链接")
    private String link;

    @Length(max = 255, message = "{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "图标")
    private String icon;

    @Schema(description = "是否固定")
    private Boolean affix;

    @Schema(description = "是否隐藏")
    private Boolean hidden;

    @Schema(description = "类型")
    private Type type;

    @Schema(description = "是否缓存")
    private Boolean cache;

    @Schema(description = "是否显示面包屑")
    public Boolean breadcrumbEnable;

    @Schema(description = "是否显示版权")
    public Boolean copyright;

    @Length(max = 64, message = "{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "视图文件路径")
    private String componentPath;

    @Length(max = 4, message = "{org.hibernate.validator.constraints.Length.message}")
    @Schema(description = "视图前缀路径")
    private String componentSuffix;

    @Schema(description = "高亮菜单标识")
    private String activeName;

    @Schema(description = "权限码")
    public List<String> auth;

    @Schema(description = "角色码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<String> role;

    @Schema(description = "用户名")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<String> user;

    public Meta() {
    }

    public Meta(Type type) {
        this.type = type;
    }

    public Meta(Type type, String title, String i18n) {
        this.type = type;
        this.title = StrUtil.emptyToNull(title);
        this.i18n = StrUtil.emptyToNull(i18n);
    }

    public void setTitle(String title) {
        this.title = StrUtil.emptyToNull(title);
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setComponentSuffix(String componentSuffix) {
        this.componentSuffix = StrUtil.emptyToNull(componentSuffix);
    }

    public void setComponentPath(String componentPath) {
        this.componentPath = StrUtil.emptyToNull(componentPath);
    }

    public void setI18n(String i18n) {
        this.i18n = StrUtil.emptyToNull(i18n);
    }

    public void setBadge(String badge) {
        this.badge = StrUtil.emptyToNull(badge);
    }

    public void setIcon(String icon) {
        this.icon = StrUtil.emptyToNull(icon);
    }

    public void setLink(String link) {
        this.link = StrUtil.emptyToNull(link);
    }

    public void setActiveName(String activeName) {
        this.activeName = StrUtil.emptyToNull(activeName);
    }

    public void setAuth(List<String> auth) {
        this.auth = auth;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public void setUser(List<String> user) {
        this.user = user;
    }
}
