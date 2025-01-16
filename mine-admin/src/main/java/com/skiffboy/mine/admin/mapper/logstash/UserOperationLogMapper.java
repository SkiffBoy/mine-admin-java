package com.skiffboy.mine.admin.mapper.logstash;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.skiffboy.mine.admin.model.entity.UserOperationLogEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

/**
 * 操作日志表 映射层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Mapper
public interface UserOperationLogMapper extends BaseMapper<UserOperationLogEntity> {

    default QueryWrapper handleSearch(Map<String, Object> params) {
        QueryWrapper query = QueryWrapper.create().from(UserOperationLogEntity.class)
                .eq(UserOperationLogEntity::getUsername, MapUtil.getStr(params, "username"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "username")))
                .eq(UserOperationLogEntity::getMethod, MapUtil.getStr(params, "method"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "method")))
                .likeLeft(UserOperationLogEntity::getRouter, MapUtil.getStr(params, "router"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "router")))
                .like(UserOperationLogEntity::getServiceName, MapUtil.getStr(params, "service_name"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "service_name")))
                .eq(UserOperationLogEntity::getIp, MapUtil.getLong(params, "ip"),
                        MapUtil.getLong(params, "ip") != null);

        String createdAt = MapUtil.getStr(params, "created_at");
        if (StrUtil.isNotEmpty(createdAt)) {
            DateTime dateTime = DateUtil.parse(createdAt, NORM_DATETIME_PATTERN);
            query = query.ge(UserOperationLogEntity::getCreatedAt, dateTime.toJdkDate());
        }

        String updatedAt = MapUtil.getStr(params, "updated_at");
        if (StrUtil.isNotEmpty(updatedAt)) {
            DateTime dateTime = DateUtil.parse(updatedAt, NORM_DATETIME_PATTERN);
            query = query.ge(UserOperationLogEntity::getUpdatedAt, dateTime.toJdkDate());
        }

        return query.orderBy(UserOperationLogEntity::getCreatedAt, false);
    }

}
