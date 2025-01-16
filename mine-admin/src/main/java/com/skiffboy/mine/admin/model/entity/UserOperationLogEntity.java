package com.skiffboy.mine.admin.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.skiffboy.mine.admin.model.vo.UserOperationLog;
import io.github.linpeilie.annotations.AutoMapper;

import java.util.Date;

/**
 * 操作日志表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@AutoMapper(target = UserOperationLog.class)
@Table(value = "user_operation_log")
public class UserOperationLogEntity {

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户名
     */
    @Column(value = "username")
    private String username;

    /**
     * 请求方式
     */
    @Column(value = "method")
    private String method;

    /**
     * 请求路由
     */
    @Column(value = "router")
    private String router;

    /**
     * 业务名称
     */
    @Column(value = "service_name")
    private String serviceName;

    /**
     * 请求IP地址
     */
    @Column(value = "ip")
    private String ip;

    /**
     * 创建时间
     */
    @Column(value = "created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
