package com.skiffboy.mine.admin.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.skiffboy.mine.admin.model.entity.UserOperationLogEntity;

import java.util.Map;

/**
 * 操作日志表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IUserOperationLogService extends IService<UserOperationLogEntity> {
    Page<UserOperationLogEntity> page(Map<String, Object> params);
}