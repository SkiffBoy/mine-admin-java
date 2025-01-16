package com.skiffboy.mine.admin.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.skiffboy.mine.admin.model.entity.UserEntity;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.admin.model.permission.Role;
import com.skiffboy.mine.admin.model.permission.RoleCode;
import com.skiffboy.mine.admin.model.request.user.UserRequest;
import com.skiffboy.mine.admin.model.request.user.UserRequest;
import com.skiffboy.mine.admin.model.vo.UserInfoVo;

import java.util.List;
import java.util.Map;

/**
 * 用户信息表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IUserService extends IService<UserEntity> {

    Page<UserEntity> page(Map<String, Object> params);

    boolean resetPassword(Long userId);

    void batchGrantRoleForUser(Long userId, List<String> roleCodes);

    List<Role> getUserRoles(Long userId);

    List<RoleCode> getUserRoleCodes(Long userId);

    List<Menu> getUserMenus(Long userId);

    void create(UserRequest user);

    boolean updateById(Long userId, UserRequest request);

    UserInfoVo getInfo(Long userId);

    /**
     * 逻辑删除
     *
     * @param ids
     */
    void deleteById(List<Long> ids);

    /**
     * 物理删除
     *
     * @param ids
     */
    void forceDeleteById(List<Long> ids);
}