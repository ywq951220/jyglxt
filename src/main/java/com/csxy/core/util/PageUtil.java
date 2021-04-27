package com.csxy.core.util;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;

public class PageUtil {

    public static Map<String, Object> getLayuiPageData(PageInfo page) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("data", page.getList());
        map.put("count", page.getTotal());
        return map;
    }
}
