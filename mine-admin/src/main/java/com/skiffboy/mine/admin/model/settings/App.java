package com.skiffboy.mine.admin.model.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Schema(description = "应用属性")
public class App {

    @Schema(description = "颜色模式", defaultValue = "light", allowableValues = {"light", "dark", "autoMode"})
    private String colorMode = "light";

    @Schema(description = "主要颜色")
    private String primaryColor = "#2563EB";

    @Schema(description = "侧边栏是否黑夜模式")
    private Boolean asideDark = false;

    @Schema(description = "使用的语言包", defaultValue = "zh_CN")
    private String useLocale = "zh_CN";

    @Schema(description = "白名单路由，白名单路由不进行权限校验", defaultValue = "[\"login\"]")
    private List<String> whiteRoute = Arrays.asList("login");

    @Schema(description = "页面布局方式", defaultValue = "columns", allowableValues = {"columns", "classic", "mixed", "banner"})
    private String layout = "columns";

    @Schema(description = "页面过场动画", defaultValue = "ma-fade", allowableValues = {"ma-fade", "ma-slide-right", "ma-slide-left", "ma-slide-down", "ma-slide-up"})
    private String pageAnimate = "ma-slide-down";

    @Schema(description = "是否显示面包屑", defaultValue = "true")
    private Boolean showBreadcrumb = true;

    @Schema(description = "是否开启水印", defaultValue = "false")
    private Boolean enableWatermark = false;

    @Schema(description = "是否加载用户设置", defaultValue = "true")
    private Boolean loadUserSetting = true;

    @Schema(description = "水印文字", defaultValue = "MineAdmin")
    private String watermarkText = "MineAdmin";

}
