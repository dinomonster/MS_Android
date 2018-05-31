package com.hr.ms.ms_android.network;

import java.util.Map;

public class ApiQueryMapUtils {
    public static void addQueryMap(String key, Object parameter, Map map) {
        if (parameter != null && !"".equals(parameter)) {
            map.put(key, parameter);
        }
    }
}
