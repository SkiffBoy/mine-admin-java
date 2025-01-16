package com.skiffboy.mine.admin.model.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SubAside {

    @Schema(description = "是否显示图标", defaultValue = "true")
    private Boolean showIcon = true;

    @Schema(description = "是否显示标题", defaultValue = "true")
    private Boolean showTitle = true;

    @Schema(description = "固定子菜单状态", defaultValue = "false")
    private Boolean fixedAsideState = false;

    @Schema(description = "是否显示折叠按钮", defaultValue = "true")
    private Boolean showCollapseButton = true;

}
