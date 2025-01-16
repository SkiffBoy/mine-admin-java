package com.skiffboy.mine.admin.model.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class WelcomePage {

    @Schema(description = "欢迎页默认地址", defaultValue = "/welcome")
    private String path = "/welcome";

    @Schema(description = "欢迎页路由名称", defaultValue = "welcome")
    private String name = "/welcome";

    @Schema(description = "欢迎页默认名称", defaultValue = "欢迎页")
    private String title = "欢迎页";

    @Schema(description = "欢迎页图标")
    private String icon = "icon-park-outline:jewelry";

}
