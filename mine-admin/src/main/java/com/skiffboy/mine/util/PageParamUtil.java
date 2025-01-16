package com.skiffboy.mine.util;

import cn.hutool.core.map.MapUtil;
import com.mybatisflex.core.paginate.Page;

import java.util.Map;

public class PageParamUtil {
    public static Page getPage(Map<String, Object> params) {
        int page = MapUtil.getInt(params, "page", 1);
        int pageSize = MapUtil.getInt(params, "page_size", 10);
        return Page.of(page, pageSize);
    }
}
