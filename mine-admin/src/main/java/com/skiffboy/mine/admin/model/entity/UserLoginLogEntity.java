package com.skiffboy.mine.admin.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.skiffboy.mine.admin.model.vo.UserLoginLog;
import io.github.linpeilie.annotations.AutoMapper;

import java.util.Date;

/**
 * 登录日志表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@AutoMapper(target = UserLoginLog.class)
@Table(value = "user_login_log")
public class UserLoginLogEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户名
     */
    @Column(value = "username")
    private String username;

    /**
     * 登录IP地址
     */
    @Column(value = "ip")
    private String ip;

    /**
     * 操作系统
     */
    @Column(value = "os")
    private String os;

    /**
     * 浏览器
     */
    @Column(value = "browser")
    private String browser;

    /**
     * 登录状态 (1成功 2失败)
     */
    @Column(value = "status")
    private Integer status;

    /**
     * 提示消息
     */
    @Column(value = "message")
    private String message;

    /**
     * 登录时间
     */
    @Column(value = "login_time")
    private Date loginTime;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setStatus(boolean isLogin) {
        this.status = isLogin ? 1 : 2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
