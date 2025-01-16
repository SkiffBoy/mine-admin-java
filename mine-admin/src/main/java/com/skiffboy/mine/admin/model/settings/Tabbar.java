package com.skiffboy.mine.admin.model.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Tabbar {

    @Schema(description = "是否显示标签页", defaultValue = "true")
    private Boolean enable = true;

    @Schema(description = "模式", allowableValues = {"rectangle", "card"})
    private String mode = "rectangle";
}
