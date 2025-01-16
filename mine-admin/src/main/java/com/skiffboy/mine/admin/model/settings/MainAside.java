package com.skiffboy.mine.admin.model.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MainAside {

    @Schema(description = "是否显示图标", defaultValue = "true")
    private Boolean showIcon = true;

    @Schema(description = "是否显示标题", defaultValue = "true")
    private Boolean showTitle = true;

    @Schema(description = "是否开启默认打开第一个路由", defaultValue = "true")
    private Boolean enableOpenFirstRoute = true;

}
