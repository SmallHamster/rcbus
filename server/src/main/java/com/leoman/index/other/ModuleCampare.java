package com.leoman.index.other;

import com.leoman.permissions.module.entity.vo.ModuleVo;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public class ModuleCampare implements Comparator<ModuleVo>{


    @Override
    public int compare(ModuleVo o1, ModuleVo o2) {
        if(o1.getIndex() < o2.getIndex()) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
