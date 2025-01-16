package com.skiffboy.mine.admin.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.skiffboy.mine.admin.model.entity.UserLoginLogEntity;

import java.util.Map;

/**
 * 登录日志表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IUserLoginLogService extends IService<UserLoginLogEntity> {

    Page<UserLoginLogEntity> page(Map<String, Object> params);

}