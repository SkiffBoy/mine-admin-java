package com.skiffboy.mine.admin.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.skiffboy.mine.admin.model.entity.RoleEntity;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.admin.model.permission.RolePermission;
import com.skiffboy.mine.admin.model.permission.User;
import com.skiffboy.mine.admin.model.request.permission.RoleRequest;

import java.util.List;
import java.util.Map;

/**
 * 角色信息表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IRoleService extends IService<RoleEntity> {

    Page<RoleEntity> page(Map<String, Object> params);

    boolean create(RoleRequest request);

    boolean updateById(Long id, RoleRequest request);

    List<Menu> getRoleMenus(Long roleId);

    List<User> getRoleUsers(Long roleId);

    List<RolePermission> getRolePermission(Long roleId);

    void batchGrantPermissionsForRole(Long roleId, List<String> permissionsCode);

    boolean existsById(Long roleId);

    void deleteById(List<Long> roleIds);
}