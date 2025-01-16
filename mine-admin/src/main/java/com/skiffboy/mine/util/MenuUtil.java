package com.skiffboy.mine.util;

import cn.hutool.core.collection.CollUtil;
import com.skiffboy.mine.admin.model.permission.Menu;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class MenuUtil {

    public static List<Menu> toTopTree(List<Menu> menus) {
        return toTree(menus, menu -> menu.getParentId() == 0);
    }

    public static List<Menu> toTree(List<Menu> menus) {
        return toTree(menus, null);
    }

    public static List<Menu> toTree(List<Menu> menus, Predicate<Menu> filter) {
        Map<Long, Menu> menuTree = new LinkedHashMap<>();
        for (Menu menu : menus) {
            menuTree.put(menu.getId(), menu);
        }
        menus.stream()
                .filter(menu -> menu.getParentId() != null)
                .forEach(menu -> {
                    Menu parentMenu = menuTree.get(menu.getParentId());
                    if (parentMenu != null) {
                        parentMenu.addChild(menu);
                    }
                });
        if (filter != null) {
            return menuTree.values().stream().filter(filter).collect(Collectors.toList());
        }
        return menuTree.values().stream().collect(Collectors.toList());

    }

    public static List<Menu> filterMenuTree(Collection<Menu> menus, Predicate<Menu> filter) {
        return menus.stream().filter(filter)
                .peek(menu -> {
                    if (CollUtil.isNotEmpty(menu.getChildren())) {
                        List<Menu> filteredChildren = filterMenuTree(menu.getChildren(), filter);
                        menu.setChildren(filteredChildren);
                    }
                })
                .collect(Collectors.toList());
    }
}
