package com.skiffboy.mine.admin.service;


import com.mybatisflex.core.service.IService;
import com.skiffboy.mine.admin.model.entity.MenuEntity;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.admin.model.permission.Role;
import com.skiffboy.mine.admin.model.request.permission.MenuRequest;

import java.util.List;
import java.util.Map;

/**
 * 菜单信息表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IMenuService extends IService<MenuEntity> {

    List<Menu> listAll();

    List<MenuEntity> list(Map<String, Object> params);

    List<Role> getRoles(Long menuId);

    MenuEntity create(MenuRequest menu);

    MenuEntity updateById(MenuRequest menu);

    MenuEntity requestToEntity(MenuRequest menu);
}