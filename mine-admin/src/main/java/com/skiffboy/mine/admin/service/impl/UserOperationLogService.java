package com.skiffboy.mine.admin.service.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.skiffboy.mine.admin.mapper.logstash.UserOperationLogMapper;
import com.skiffboy.mine.admin.model.entity.UserOperationLogEntity;
import com.skiffboy.mine.admin.service.IUserOperationLogService;
import com.skiffboy.mine.util.PageParamUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 操作日志表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class UserOperationLogService extends ServiceImpl<UserOperationLogMapper, UserOperationLogEntity> implements IUserOperationLogService {

    @Override
    public Page<UserOperationLogEntity> page(Map<String, Object> params) {
        return page(PageParamUtil.getPage(params), mapper.handleSearch(params));
    }
}