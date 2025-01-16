package com.skiffboy.mine.admin.model.settings;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BackendSetting {

    @Schema(description = "应用属性")
    private App app;

    @Schema(description = "欢迎页")
    private WelcomePage welcomePage;

    @Schema(description = "主菜单")
    private MainAside mainAside;

    @Schema(description = "子菜单设置")
    private SubAside subAside;

    @Schema(description = "标签页")
    private Tabbar tabbar;

    @Schema(description = "底部版权设置")
    private Copyright copyright;

}
