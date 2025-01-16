package com.skiffboy.mine.admin.model.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "版权信息属性")
public class Copyright {

    @Schema(description = "是否开启底部版权，同时在路由 meta 对象里可以单独设置某个路由是否显示底部版权信息", defaultValue = "true")
    private Boolean enable = true;

    @Schema(description = "网站运行日期")
    private String dates = "";

    @Schema(description = "公司名称")
    private String company = "";

    @Schema(description = "网站地址", defaultValue = "")
    private String website = "";

    @Schema(description = "网站备案号", defaultValue = "")
    private String putOnRecord = "";

}
