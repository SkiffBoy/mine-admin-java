package com.skiffboy.mine.admin.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import com.skiffboy.mine.admin.enums.common.Status;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.admin.model.permission.Meta;
import io.github.linpeilie.annotations.AutoMapper;

import java.util.Date;

/**
 * 菜单信息表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@AutoMapper(target = Menu.class)
@Table(value = "menu")
public class MenuEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 父ID
     */
    @Column(value = "parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @Column(value = "name")
    private String name;

    /**
     * 附加属性
     */
    @Column(value = "meta", typeHandler = Fastjson2TypeHandler.class)
    private Meta meta;

    /**
     * 路径
     */
    @Column(value = "path")
    private String path;

    /**
     * 组件路径
     */
    @Column(value = "component")
    private String component;

    /**
     * 重定向地址
     */
    @Column(value = "redirect")
    private String redirect;

    /**
     * 状态:1=正常,2=停用
     */
    @Column(value = "status")
    private Status status;

    /**
     * 排序
     */
    @Column(value = "sort")
    private Integer sort;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
