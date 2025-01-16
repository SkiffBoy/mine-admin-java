package com.skiffboy.mine.admin.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.skiffboy.mine.admin.enums.common.Status;
import com.skiffboy.mine.admin.enums.menu.Type;
import com.skiffboy.mine.admin.mapper.RoleBelongsMenuMapper;
import com.skiffboy.mine.admin.mapper.permission.MenuMapper;
import com.skiffboy.mine.admin.model.entity.MenuEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.admin.model.permission.Meta;
import com.skiffboy.mine.admin.model.permission.Role;
import com.skiffboy.mine.admin.model.request.permission.MenuRequest;
import com.skiffboy.mine.admin.service.IMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 菜单信息表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, MenuEntity> implements IMenuService {
    @Resource
    private RoleBelongsMenuMapper roleBelongsMenuMapper;

    @Override
    public List<Menu> listAll() {
        QueryWrapper query = QueryWrapper.create().from(MenuEntity.class)
                .orderBy(MenuEntity::getSort, true);
        List<Menu> menus = mapper.selectListByQueryAs(query, Menu.class);
        return menus;
    }

    @Override
    public List<MenuEntity> list(Map<String, Object> params) {
        return mapper.selectListByQuery(mapper.handleSearch(params));
    }

    @Override
    public List<Role> getRoles(Long menuId) {
        return roleBelongsMenuMapper.selectRolesByMenuId(menuId);
    }

    @Override
    public MenuEntity create(MenuRequest menu) {
        MenuEntity model = requestToEntity(menu);
        model.setId(null);
        model.setCreatedBy(CurrentUser.getId());
        model.setCreatedAt(new Date());

        if (super.save(model)) {
            Meta meta = menu.getMeta();
            if (meta != null && Type.Menu == (meta.getType()) && CollUtil.isNotEmpty(menu.getBtnPermission())) {
                for (MenuRequest item : menu.getBtnPermission()) {
                    String title = item.getMeta().getTitle();
                    String i18n = item.getMeta().getI18n();

                    MenuEntity button = new MenuEntity();
                    button.setParentId(model.getId());
                    button.setName(item.getName());
                    button.setSort(0);
                    button.setStatus(Status.Normal);
                    button.setMeta(new Meta(Type.Button, title, i18n));

                    super.save(button);
                }
            }
        }
        return model;
    }

    @Override
    public MenuEntity updateById(MenuRequest menu) {
        MenuEntity model = requestToEntity(menu);
        model.setUpdatedBy(CurrentUser.getId());
        model.setUpdatedAt(new Date());

        if (super.updateById(model)) {
            Meta meta = menu.getMeta();
            if (meta != null && Type.Menu.equals(meta.getType()) && CollUtil.isNotEmpty(menu.getBtnPermission())) {
                for (MenuRequest item : menu.getBtnPermission()) {
                    if (item.getMeta() != null && Type.Button.equals(item.getMeta().getType())) {
                        String title = item.getMeta().getTitle();
                        String i18n = item.getMeta().getI18n();

                        MenuEntity button = new MenuEntity();
                        button.setName(item.getName());
                        button.setMeta(new Meta(Type.Button, title, i18n));
                        if (item.getId() != null) {
                            button.setId(item.getId());
                            super.updateById(button);
                        } else {
                            button.setParentId(model.getId());
                            super.save(button);
                        }
                    }
                }
            }
        }
        return model;
    }

    @Override
    public MenuEntity requestToEntity(MenuRequest menu) {
        MenuEntity model = new MenuEntity();
        model.setId(menu.getId());
        model.setParentId(menu.getParentId());
        model.setName(menu.getName());
        model.setMeta(menu.getMeta());
        model.setPath(menu.getPath());
        model.setComponent(menu.getComponent());
        model.setRedirect(menu.getRedirect());
        model.setStatus(menu.getStatus());
        model.setSort(menu.getSort());
        model.setRemark(menu.getRemark());
        return model;
    }
}