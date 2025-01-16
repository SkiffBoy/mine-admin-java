package com.skiffboy.mine.admin.mapper;

import com.mybatisflex.core.BaseMapper;
import com.skiffboy.mine.admin.model.entity.UserBelongsRoleEntity;
import com.skiffboy.mine.admin.model.permission.Role;
import com.skiffboy.mine.admin.model.permission.RoleCode;
import com.skiffboy.mine.admin.model.permission.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 映射层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Mapper
public interface UserBelongsRoleMapper extends BaseMapper<UserBelongsRoleEntity> {

    List<User> selectUsersByRoleId(Long roleId);

    List<Role> selectRolesByUserId(Long userId);

    List<RoleCode> selectUserRoles(Long userId);

    List<Long> selectRoleIdsByUserId(Long userId);
}
