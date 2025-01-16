package com.skiffboy.mine.admin.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skiffboy.mine.admin.enums.common.StorageMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(title = "Attachment")
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(name = "storage_mode", description = "存储模式 (1 本地 2 阿里云 3 七牛云 4 腾讯云)")
    @JsonProperty(value = "storage_mode")
    private Integer storageMode;

    public void setStorageMode(StorageMode storageMode) {
        if (storageMode != null) {
            this.storageMode = storageMode.getValue();
        }
    }

    @Schema(name = "origin_name", description = "原文件名")
    @JsonProperty(value = "origin_name")
    private String originName;

    @Schema(name = "object_name", description = "新文件名")
    @JsonProperty(value = "object_name")
    private String objectName;

    @Schema(description = "文件hash")
    private String hash;

    @Schema(name = "mime_type", description = "资源类型")
    @JsonProperty(value = "mime_type")
    private String mimeType;

    @Schema(name = "storage_path", description = "存储目录")
    @JsonProperty(value = "storage_path")
    private String storagePath;

    @Schema(description = "文件后缀")
    private String suffix;

    @Schema(name = "size_byte", description = "字节数")
    @JsonProperty(value = "size_byte")
    private Long sizeByte;

    @Schema(name = "size_info", description = "文件大小")
    @JsonProperty(value = "size_info")
    private String sizeInfo;

    @Schema(description = "url地址")
    private String url;

    @Schema(name = "created_by", description = "创建者")
    @JsonProperty(value = "created_by")
    private Long createdBy;

    @Schema(name = "updated_by", description = "更新者")
    @JsonProperty(value = "updated_by")
    private Long updatedBy;

    @Schema(name = "created_at", description = "创建时间")
    @JsonProperty(value = "created_at")
    private Date createdAt;

    @Schema(name = "updated_at", description = "更新时间")
    @JsonProperty(value = "updated_at")
    private Date updatedAt;

    @Schema(description = "备注")
    private String remark;
}
