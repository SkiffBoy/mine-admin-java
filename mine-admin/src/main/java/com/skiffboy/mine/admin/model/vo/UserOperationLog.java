package com.skiffboy.mine.admin.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(title = "UserOperationLog")
public class UserOperationLog {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "用户名")
    public String username;

    @Schema(description = "请求方式")
    public String method;

    @Schema(description = "请求路由")
    public String router;

    @Schema(name = "service_name", description = "业务名称")
    @JsonProperty("service_name")
    public String serviceName;

    @Schema(description = "请求IP地址")
    public String ip;

    @Schema(name = "ip_location", description = "IP所属地")
    @JsonProperty("ip_location")
    public String ipLocation;

    @Schema(name = "request_data", description = "请求数据")
    @JsonProperty("request_data")
    public String requestData;

    @Schema(name = "response_code", description = "响应状态码")
    @JsonProperty("response_code")
    public String responseCode;

    @Schema(name = "response_data", description = "响应数据")
    @JsonProperty("response_data")
    public String responseData;

    @Schema(name = "created_by", description = "创建者")
    @JsonProperty("created_by")
    public Long createdBy;

    @Schema(name = "updated_by", description = "更新者")
    @JsonProperty("updated_by")
    public Long updatedBy;

    @Schema(name = "created_at", description = "创建时间")
    @JsonProperty("created_at")
    public Date createdAt;

    @Schema(name = "updated_at", description = "更新时间")
    @JsonProperty("updated_at")
    public Date updatedAt;

    @Schema(description = "备注")
    public String remark;
}
