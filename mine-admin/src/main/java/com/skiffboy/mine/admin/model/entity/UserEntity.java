package com.skiffboy.mine.admin.model.entity;

import cn.dev33.satoken.secure.BCrypt;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import com.skiffboy.mine.admin.enums.common.Status;
import com.skiffboy.mine.admin.enums.user.UserType;
import com.skiffboy.mine.admin.model.permission.User;
import com.skiffboy.mine.admin.model.settings.BackendSetting;
import com.skiffboy.mine.admin.model.vo.UserInfoVo;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;

import java.util.Date;

/**
 * 用户信息表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@AutoMappers({
        @AutoMapper(target = User.class),
        @AutoMapper(target = UserInfoVo.class, reverseConvertGenerate = false)
})
@Table(value = "user")
public class UserEntity {

    /**
     * 用户ID,主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户名
     */
    @Column(value = "username")
    private String username;

    /**
     * 密码
     */
    @Column(value = "password")
    private String password;

    /**
     * 用户类型:100=系统用户
     */
    @Column(value = "user_type")
    private UserType userType;

    /**
     * 用户昵称
     */
    @Column(value = "nickname")
    private String nickname;

    /**
     * 手机
     */
    @Column(value = "phone")
    private String phone;

    /**
     * 用户邮箱
     */
    @Column(value = "email")
    private String email;

    /**
     * 用户头像
     */
    @Column(value = "avatar")
    private String avatar;

    /**
     * 个人签名
     */
    @Column(value = "signed")
    private String signed;

    /**
     * 状态:1=正常,2=停用
     */
    @Column(value = "status")
    private Status status;

    /**
     * 最后登陆IP
     */
    @Column(value = "login_ip")
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @Column(value = "login_time")
    private Date loginTime;

    /**
     * 后台设置数据
     */
    @Column(value = "backend_setting", typeHandler = Fastjson2TypeHandler.class)
    private BackendSetting backendSetting;

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

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSigned() {
        return signed;
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public BackendSetting getBackendSetting() {
        return backendSetting;
    }

    public void setBackendSetting(BackendSetting backendSetting) {
        this.backendSetting = backendSetting;
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
