package com.skiffboy.mine.admin.mapper.permission;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.skiffboy.mine.admin.enums.user.UserType;
import com.skiffboy.mine.admin.model.entity.RoleEntity;
import com.skiffboy.mine.admin.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

/**
 * 用户信息表 映射层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    default UserEntity findByUnameType(String username, UserType userType) {
        if (userType == null) {
            userType = UserType.SYSTEM;
        }
        QueryWrapper queryWrapper = QueryWrapper.create().from(UserEntity.class)
                .eq(UserEntity::getUsername, username)
                .eq(UserEntity::getUserType, userType.getCode());
        UserEntity user = selectOneByQuery(queryWrapper);
        return user;
    }

    default QueryWrapper handleSearch(Map<String, Object> params) {
        QueryWrapper query = QueryWrapper.create().from(UserEntity.class)
                .eq(UserEntity::getUsername, MapUtil.getStr(params, "unique_username"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "unique_username")))
                .like(UserEntity::getUsername, MapUtil.getStr(params, "username"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "username")))
                .like(UserEntity::getNickname, MapUtil.getStr(params, "nickname"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "nickname")))
                .like(UserEntity::getPhone, MapUtil.getStr(params, "phone"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "phone")))
                .like(UserEntity::getEmail, MapUtil.getStr(params, "email"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "email")))
                .eq(UserEntity::getStatus, MapUtil.getInt(params, "status"),
                        MapUtil.getInt(params, "status") != null)
                .eq(UserEntity::getUserType, MapUtil.getInt(params, "user_type"),
                        MapUtil.getInt(params, "user_type") != null);
        String createdAt = MapUtil.getStr(params, "created_at");
        if (StrUtil.isNotEmpty(createdAt)) {
            DateTime dateTime = DateUtil.parse(createdAt, NORM_DATETIME_PATTERN);
            query = query.ge(RoleEntity::getCreatedAt, dateTime.toJdkDate());
        }
        return query;
    }

}
