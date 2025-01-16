package com.skiffboy.mine.admin.service.impl;


import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.skiffboy.mine.admin.exception.BusinessException;
import com.skiffboy.mine.admin.mapper.RoleBelongsMenuMapper;
import com.skiffboy.mine.admin.mapper.UserBelongsRoleMapper;
import com.skiffboy.mine.admin.mapper.permission.RoleMapper;
import com.skiffboy.mine.admin.mapper.permission.UserMapper;
import com.skiffboy.mine.admin.model.ResultCode;
import com.skiffboy.mine.admin.model.entity.RoleEntity;
import com.skiffboy.mine.admin.model.entity.UserBelongsRoleEntity;
import com.skiffboy.mine.admin.model.entity.UserEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.admin.model.permission.Role;
import com.skiffboy.mine.admin.model.permission.RoleCode;
import com.skiffboy.mine.admin.model.request.user.UserRequest;
import com.skiffboy.mine.admin.model.request.user.UserRequest;
import com.skiffboy.mine.admin.model.vo.UserInfoVo;
import com.skiffboy.mine.admin.service.IUserService;
import com.skiffboy.mine.util.PageParamUtil;
import io.github.linpeilie.Converter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户信息表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

    @Resource
    private UserBelongsRoleMapper userBelongsRoleMapper;
    @Resource
    private RoleBelongsMenuMapper roleBelongsMenuMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private Converter converter;
    private static final String DEFAULT_PASSWORD = "123456";

    @Override
    public Page<UserEntity> page(Map<String, Object> params) {
        return page(PageParamUtil.getPage(params), mapper.handleSearch(params));
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        return userBelongsRoleMapper.selectRolesByUserId(userId);
    }

    @Override
    public List<RoleCode> getUserRoleCodes(Long userId) {
        return userBelongsRoleMapper.selectUserRoles(userId);
    }

    @Override
    public List<Menu> getUserMenus(Long userId) {
        return roleBelongsMenuMapper.selectMenusByUserId(userId);
    }

    @Override
    public void create(UserRequest request) {
        UserEntity entity = converter.convert(request, UserEntity.class);
        if (StrUtil.isEmpty(request.getPassword())) {
            request.setPassword(BCrypt.hashpw(DEFAULT_PASSWORD));
        } else {
            entity.setPassword(BCrypt.hashpw(request.getPassword()));
        }
        entity.setCreatedBy(CurrentUser.getId());
        entity.setCreatedAt(new Date());
        try {
            super.save(entity);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new BusinessException("user.username_exist", e.getCause());
        }
    }

    @Override
    public boolean updateById(Long userId, UserRequest request) {
        if (!existsById(userId)) {
            throw new BusinessException(ResultCode.FAIL);
        }
        UserEntity entity = converter.convert(request, UserEntity.class);
        entity.setId(userId);
        entity.setPassword(null);
        entity.setUpdatedBy(CurrentUser.getId());
        entity.setUpdatedAt(new Date());
        try {
            return super.updateById(entity);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new BusinessException("user.username_exist", e.getCause());
        }
    }

    @Override
    public UserInfoVo getInfo(Long userId) {
        UserEntity entity = mapper.selectOneById(userId);
        UserInfoVo user = converter.convert(entity, UserInfoVo.class);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(List<Long> userIds) {
        for (Long userId : userIds) {
            mapper.deleteById(userId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void forceDeleteById(List<Long> userIds) {
        for (Long userId : userIds) {
            // 解除关联的角色
            userBelongsRoleMapper.deleteByQuery(
                    QueryWrapper.create().from(UserBelongsRoleEntity.class)
                            .eq(UserBelongsRoleEntity::getUserId, userId)
            );
            // 最后执行删除
            boolean success = mapper.deleteById(userId) != 0;
            if (!success) {
                throw new BusinessException(ResultCode.FAIL);
            }
        }
    }

    public boolean existsById(Long userId) {
        return mapper.selectCountByQuery(QueryWrapper.create().from(UserEntity.class).eq(UserEntity::getId, userId)) > 0;
    }

    @Override
    public boolean resetPassword(Long userId) {
        if (userId == null) {
            return false;
        }
        if (!existsById(userId)) {
            throw new BusinessException(ResultCode.FAIL);
        }
        String resetPassword = BCrypt.hashpw(DEFAULT_PASSWORD);

        return UpdateChain.of(UserEntity.class)
                .set(UserEntity::getPassword, resetPassword)
                .where(UserEntity::getId).eq(userId)
                .update();
    }

    @Override
    public void batchGrantRoleForUser(Long userId, List<String> roleCodes) {
        if (CollUtil.isEmpty(roleCodes)) {
            // 至少需要一个角色
            throw new BusinessException("user.user_must_have_role");
        }
        // 当前用户已有角色
        List<Long> deleteRoleIds = userBelongsRoleMapper.selectRoleIdsByUserId(userId);

        List<RoleEntity> grantRoles = roleMapper.selectListByQuery(
                QueryWrapper.create().from(RoleEntity.class)
                        .in(RoleEntity::getCode, roleCodes)
        );
        List<Long> grantRoleIds = grantRoles.stream().map(RoleEntity::getId).collect(Collectors.toList());
        for (Iterator<Long> iterator = grantRoleIds.iterator(); iterator.hasNext(); ) {
            Long roleId = iterator.next();
            // 避免重复授权
            if (deleteRoleIds.contains(roleId)) {
                deleteRoleIds.remove(roleId);
                iterator.remove();
            }
        }
        List<UserBelongsRoleEntity> insertList = grantRoleIds.stream().map(roleId -> {
            UserBelongsRoleEntity entity = new UserBelongsRoleEntity();
            entity.setRoleId(roleId);
            entity.setUserId(userId);
            entity.setCreatedAt(new Date());
            return entity;
        }).collect(Collectors.toList());

        // 维护关联关系-新增
        if (CollUtil.isNotEmpty(insertList)) {
            userBelongsRoleMapper.insertBatch(insertList);
        }
        // 删除关联关系
        if (CollUtil.isNotEmpty(deleteRoleIds)) {
            userBelongsRoleMapper.deleteByQuery(
                    QueryWrapper.create().from(UserBelongsRoleEntity.class)
                            .in(UserBelongsRoleEntity::getRoleId, deleteRoleIds)
            );
        }
    }

}