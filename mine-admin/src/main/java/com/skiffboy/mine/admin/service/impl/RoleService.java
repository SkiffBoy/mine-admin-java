package com.skiffboy.mine.admin.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.skiffboy.mine.admin.exception.BusinessException;
import com.skiffboy.mine.admin.mapper.RoleBelongsMenuMapper;
import com.skiffboy.mine.admin.mapper.UserBelongsRoleMapper;
import com.skiffboy.mine.admin.mapper.permission.MenuMapper;
import com.skiffboy.mine.admin.mapper.permission.RoleMapper;
import com.skiffboy.mine.admin.model.ResultCode;
import com.skiffboy.mine.admin.model.entity.MenuEntity;
import com.skiffboy.mine.admin.model.entity.RoleBelongsMenuEntity;
import com.skiffboy.mine.admin.model.entity.RoleEntity;
import com.skiffboy.mine.admin.model.entity.UserBelongsRoleEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.admin.model.permission.RolePermission;
import com.skiffboy.mine.admin.model.permission.User;
import com.skiffboy.mine.admin.model.request.permission.RoleRequest;
import com.skiffboy.mine.admin.service.IRoleService;
import com.skiffboy.mine.util.PageParamUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色信息表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, RoleEntity> implements IRoleService {

    @Resource
    private RoleBelongsMenuMapper roleBelongsMenuMapper;
    @Resource
    private UserBelongsRoleMapper userBelongsRoleMapper;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public Page<RoleEntity> page(Map<String, Object> params) {
        return page(PageParamUtil.getPage(params), mapper.handleSearch(params));
    }

    @Override
    public boolean create(RoleRequest request) {
        RoleEntity entity = requestToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(CurrentUser.getId());

        try {
            return super.save(entity);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new BusinessException("role.code_exist", e.getCause());
        }
    }

    @Override
    public boolean updateById(Long id, RoleRequest request) {
        RoleEntity entity = requestToEntity(request);
        entity.setId(id);
        entity.setUpdatedAt(new Date());
        entity.setUpdatedBy(CurrentUser.getId());

        return super.updateById(entity);
    }

    private RoleEntity requestToEntity(RoleRequest request) {
        RoleEntity entity = new RoleEntity();
        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setStatus(request.getStatus());
        entity.setSort(request.getSort());
        entity.setRemark(request.getRemark());
        return entity;
    }

    @Override
    public List<Menu> getRoleMenus(Long roleId) {
        return roleBelongsMenuMapper.selectMenusByRoleId(roleId);
    }

    @Override
    public List<User> getRoleUsers(Long roleId) {
        return userBelongsRoleMapper.selectUsersByRoleId(roleId);
    }

    @Override
    public List<RolePermission> getRolePermission(Long roleId) {
        return roleBelongsMenuMapper.selectPermissionByRoleId(roleId);
    }

    @Override
    public void batchGrantPermissionsForRole(Long roleId, List<String> permissionsCode) {
        List<Long> deleteMenuIds = roleBelongsMenuMapper.selectMenuIdsByRoleId(roleId);
        if (CollUtil.isEmpty(permissionsCode)) {
            // 解除授权
            roleBelongsMenuMapper.deleteByQuery(
                    QueryWrapper.create().from(RoleBelongsMenuEntity.class)
                            .in(RoleBelongsMenuEntity::getMenuId, deleteMenuIds)
            );
            return;
        }
        List<Long> grantMenuIds = menuMapper.selectListByQueryAs(
                QueryWrapper.create().from(MenuEntity.class)
                        .select(MenuEntity::getId).in(MenuEntity::getName, permissionsCode), Long.class);

        if (CollUtil.isNotEmpty(deleteMenuIds)) {
            for (Iterator<Long> iterator = grantMenuIds.iterator(); iterator.hasNext(); ) {
                Long menuId = iterator.next();
                if (deleteMenuIds.contains(menuId)) {
                    deleteMenuIds.remove(menuId);
                    iterator.remove();
                }
            }
        }

        List<RoleBelongsMenuEntity> insertList = grantMenuIds.stream().map(menuId -> {
            RoleBelongsMenuEntity entity = new RoleBelongsMenuEntity();
            entity.setRoleId(roleId);
            entity.setMenuId(menuId);
            entity.setCreatedAt(new Date());
            return entity;
        }).collect(Collectors.toList());
        // 维护关联关系-新增
        if (CollUtil.isNotEmpty(insertList)) {
            roleBelongsMenuMapper.insertBatch(insertList);
        }
        // 删除关联关系
        if (CollUtil.isNotEmpty(deleteMenuIds)) {
            roleBelongsMenuMapper.deleteByQuery(
                    QueryWrapper.create().from(RoleBelongsMenuEntity.class)
                            .in(RoleBelongsMenuEntity::getMenuId, deleteMenuIds)
            );
        }
    }

    @Override
    public boolean existsById(Long roleId) {
        return mapper.selectCountByQuery(QueryWrapper.create().from(RoleEntity.class).eq(RoleEntity::getId, roleId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(List<Long> roleIds) {
        for (Long roleId : roleIds) {
            // 解除关联的菜单
            roleBelongsMenuMapper.deleteByQuery(
                    QueryWrapper.create().from(RoleBelongsMenuEntity.class)
                            .eq(RoleBelongsMenuEntity::getRoleId, roleId)
            );
            // 解除关联的用户
            userBelongsRoleMapper.deleteByQuery(
                    QueryWrapper.create().from(UserBelongsRoleEntity.class)
                            .eq(UserBelongsRoleEntity::getRoleId, roleId)
            );
            // 最后执行删除
            boolean success = mapper.deleteById(roleId) != 0;
            if (!success) {
                throw new BusinessException(ResultCode.FAIL);
            }
        }
    }
}