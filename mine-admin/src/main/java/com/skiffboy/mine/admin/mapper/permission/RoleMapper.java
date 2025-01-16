package com.skiffboy.mine.admin.mapper.permission;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.skiffboy.mine.admin.model.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

/**
 * 角色信息表 映射层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {

    default QueryWrapper handleSearch(Map<String, Object> params) {
        String createdAt = MapUtil.getStr(params, "created_at");
        QueryWrapper query = QueryWrapper.create().from(RoleEntity.class)
                .like(RoleEntity::getName, MapUtil.getStr(params, "name"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "name")))
                .like(RoleEntity::getCode, MapUtil.getStr(params, "code"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "code")))
                .eq(RoleEntity::getStatus, MapUtil.getInt(params, "status"),
                        MapUtil.getInt(params, "status") != null);
        if (StrUtil.isNotEmpty(createdAt)) {
            DateTime dateTime = DateUtil.parse(createdAt, NORM_DATETIME_PATTERN);
            query = query.ge(RoleEntity::getCreatedAt, dateTime.toJdkDate());
        }
        return query;
    }

}
