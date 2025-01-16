package com.skiffboy.mine.admin.mapper.logstash;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.skiffboy.mine.admin.model.entity.UserLoginLogEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

/**
 * 登录日志表 映射层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Mapper
public interface UserLoginLogMapper extends BaseMapper<UserLoginLogEntity> {

    default QueryWrapper handleSearch(Map<String, Object> params) {
        String loginTime = MapUtil.getStr(params, "login_time");

        QueryWrapper query = QueryWrapper.create().from(UserLoginLogEntity.class)
                .eq(UserLoginLogEntity::getUsername, MapUtil.getStr(params, "username"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "username")))
                .eq(UserLoginLogEntity::getIp, MapUtil.getStr(params, "ip"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "ip")))
                .like(UserLoginLogEntity::getOs, MapUtil.getStr(params, "os"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "os")))
                .like(UserLoginLogEntity::getBrowser, MapUtil.getStr(params, "browser"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "browser")))
                .eq(UserLoginLogEntity::getStatus, MapUtil.getStr(params, "status"),
                        MapUtil.getInt(params, "status") != null)
                .like(UserLoginLogEntity::getMessage, MapUtil.getStr(params, "message"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "message")))
                .like(UserLoginLogEntity::getRemark, MapUtil.getStr(params, "remark"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "remark")));

        if (StrUtil.isNotEmpty(loginTime)) {
            DateTime dateTime = DateUtil.parse(loginTime, NORM_DATETIME_PATTERN);
            query = query.ge(UserLoginLogEntity::getLoginTime, dateTime.toJdkDate());
        }
        return query.orderBy(UserLoginLogEntity::getLoginTime, false);
    }
}
