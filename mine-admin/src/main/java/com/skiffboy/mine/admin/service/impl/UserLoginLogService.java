package com.skiffboy.mine.admin.service.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.skiffboy.mine.admin.mapper.logstash.UserLoginLogMapper;
import com.skiffboy.mine.admin.model.entity.UserLoginLogEntity;
import com.skiffboy.mine.admin.service.IUserLoginLogService;
import com.skiffboy.mine.util.PageParamUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 登录日志表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class UserLoginLogService extends ServiceImpl<UserLoginLogMapper, UserLoginLogEntity> implements IUserLoginLogService {

    @Override
    public Page<UserLoginLogEntity> page(Map<String, Object> params) {
        return page(PageParamUtil.getPage(params), mapper.handleSearch(params));
    }

}