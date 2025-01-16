package com.skiffboy.mine.admin.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.skiffboy.mine.admin.enums.common.StorageMode;
import com.skiffboy.mine.admin.model.vo.Attachment;
import io.github.linpeilie.annotations.AutoMapper;

import java.util.Date;

/**
 * 上传文件信息表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@AutoMapper(target = Attachment.class, reverseConvertGenerate = false)
@Table(value = "attachment")
public class AttachmentEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 存储模式:local=本地,oss=阿里云,qiniu=七牛云,cos=腾讯云
     */
    @Column(value = "storage_mode")
    private StorageMode storageMode;

    /**
     * 原文件名
     */
    @Column(value = "origin_name")
    private String originName;

    /**
     * 新文件名
     */
    @Column(value = "object_name")
    private String objectName;

    /**
     * 文件hash
     */
    @Column(value = "hash")
    private String hash;

    /**
     * 资源类型
     */
    @Column(value = "mime_type")
    private String mimeType;

    /**
     * 存储目录
     */
    @Column(value = "storage_path")
    private String storagePath;

    /**
     * 文件后缀
     */
    @Column(value = "suffix")
    private String suffix;

    /**
     * 字节数
     */
    @Column(value = "size_byte")
    private Long sizeByte;

    /**
     * 文件大小
     */
    @Column(value = "size_info")
    private String sizeInfo;

    /**
     * url地址
     */
    @Column(value = "url")
    private String url;

    /**
     * 创建者
     */
    @Column(value = "created_by")
    private Long createdBy;

    /**
     * 更新者
     */
    @Column(value = "updated_by")
    private Long updatedBy;

    @Column(value = "created_at")
    private Date createdAt;

    @Column(value = "updated_at")
    private Date updatedAt;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StorageMode getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(StorageMode storageMode) {
        this.storageMode = storageMode;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Long getSizeByte() {
        return sizeByte;
    }

    public void setSizeByte(Long sizeByte) {
        this.sizeByte = sizeByte;
    }

    public String getSizeInfo() {
        return sizeInfo;
    }

    public void setSizeInfo(String sizeInfo) {
        this.sizeInfo = sizeInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
