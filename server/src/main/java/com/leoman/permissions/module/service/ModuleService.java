package com.leoman.permissions.module.service;

import com.leoman.common.service.GenericManager;
import com.leoman.permissions.module.entity.Module;
import com.leoman.permissions.module.entity.vo.ModuleVo;

import java.math.BigInteger;
import java.util.List;

/**
 * 模块service
 * Created by yesong on 2016/8/29.
 */
public interface ModuleService extends GenericManager<Module>{

    public void deleteModules(Long id);

    public void saveModule(Module module, Long checkId);

    public List<ModuleVo> findListModuleVo();

    public List<Long> findListHasModuleVo(Long roleId);

    public List<ModuleVo> findListModuleByUserId(Long userId);

    public List<ModuleVo> findInModuelIds(List<BigInteger> moduleIds);
}
