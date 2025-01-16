package com.skiffboy.mine.admin.mapper.permission;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.skiffboy.mine.admin.enums.common.Status;
import com.skiffboy.mine.admin.model.entity.MenuEntity;
import com.skiffboy.mine.admin.model.entity.RoleEntity;
import com.skiffboy.mine.admin.model.permission.Menu;
import com.skiffboy.mine.util.MenuUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

/**
 * 菜单信息表 映射层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuEntity> {

    default List<Menu> listAllMenuTree() {
        List<Menu> treeList = this.listAllMenuTree(menu -> Status.Normal.getCode() == menu.getStatus());
        return treeList.stream().filter(menu -> menu.getParentId() == 0).toList();
    }

    default List<Menu> listAllMenuTree(Predicate<Menu> filter) {
        List<Menu> menus = this.selectListByQueryAs(
                QueryWrapper.create().from(MenuEntity.class)
                        .orderBy(MenuEntity::getSort, true), Menu.class);

        if (filter != null) {
            menus = menus.stream().filter(filter).collect(Collectors.toList());
        }

        return MenuUtil.toTree(menus);
    }


    default QueryWrapper handleSearch(Map<String, Object> params) {
        QueryWrapper query = QueryWrapper.create().from(MenuEntity.class)
                .eq(MenuEntity::getStatus, MapUtil.getInt(params, "status"),
                        MapUtil.getInt(params, "status") != null)
                .eq(MenuEntity::getParentId, MapUtil.getInt(params, "parent_id"),
                        MapUtil.getInt(params, "parent_id") != null);
        if (StrUtil.isNotEmpty(MapUtil.getStr(params, "code"))) {
            query = query.in(MenuEntity::getName, MapUtil.getStr(params, "code"));
        }
        if (StrUtil.isNotEmpty(MapUtil.getStr(params, "name"))) {
            query = query.in(MenuEntity::getName, MapUtil.getStr(params, "name"));
        }
        String createdAt = MapUtil.getStr(params, "created_at");
        if (StrUtil.isNotEmpty(createdAt)) {
            DateTime dateTime = DateUtil.parse(createdAt, NORM_DATETIME_PATTERN);
            query = query.ge(RoleEntity::getCreatedAt, dateTime.toJdkDate());
        }
        return query;
    }

}
