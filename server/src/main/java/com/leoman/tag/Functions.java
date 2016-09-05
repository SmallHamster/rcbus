package com.leoman.tag;

import com.leoman.permissions.module.entity.vo.ModuleVo;

import java.util.List;

/**
 * 自定义jstl函数
 * Created by yesong on 2016/8/29.
 */
public class Functions {

    /**
     * @param list
     * @return
     */
    public static boolean contains(Long moduleId, List<Long> list) {
        if (list == null || list.isEmpty()) return false;
//        for (ModuleVo moduleVo : list) {
//            if(moduleVo.getId().longValue() == moduleId.longValue()) {
//                return true;
//            }
//        }
        if (list.contains(moduleId)) {
            return true;
        }
        return false;
    }
}
