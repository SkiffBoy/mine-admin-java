package com.skiffboy.mine.admin.mapper;

import com.mybatisflex.core.BaseMapper;
import com.skiffboy.mine.admin.model.entity.RoleBelongsMenuEntity;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.admin.model.permission.Role;
import com.skiffboy.mine.admin.model.permission.RolePermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 映射层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Mapper
public interface RoleBelongsMenuMapper extends BaseMapper<RoleBelongsMenuEntity> {

    List<Menu> selectMenusByRoleId(Long roleId);

    List<RolePermission> selectPermissionByRoleId(Long roleId);

    List<Long> selectMenuIdsByRoleId(Long roleId);

    List<Role> selectRolesByMenuId(Long menuId);

    List<Menu> selectMenusByUserId(Long userId);
}
