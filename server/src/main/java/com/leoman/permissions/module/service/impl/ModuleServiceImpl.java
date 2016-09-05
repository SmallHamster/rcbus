package com.leoman.permissions.module.service.impl;

import com.leoman.common.core.bean.Role;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.permissions.module.dao.ModuleDao;
import com.leoman.permissions.module.entity.Module;
import com.leoman.permissions.module.entity.vo.ModuleVo;
import com.leoman.permissions.module.service.ModuleService;
import com.leoman.permissions.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by yesong on 2016/8/29.
 */
@Service
public class ModuleServiceImpl extends GenericManagerImpl<Module, ModuleDao> implements ModuleService {

    @Autowired
    private ModuleDao dao;

    @Autowired
    private RoleService roleService;

    @Override
    public void deleteModules(Long id) {
        List<Module> subModules = this.queryByProperty("pid", id);
        this.deletes(subModules);
        this.delete(this.queryByPK(id));
    }

    @Override
    public void saveModule(Module module, Long checkId) {
        if (module.getId() == null) {
            create(module, checkId);
        } else {
            this.save(module);
        }
    }

    @Transactional
    @Override
    public List<ModuleVo> findListModuleVo() {

        com.leoman.common.service.Query query = com.leoman.common.service.Query.forClass(Module.class, this);
        query.isNull("superModule.id");
        List<Module> list = this.queryAll(query);
        List<ModuleVo> voList = entityToVo(list);
        return voList;
    }

    @Transactional
    @Override
    public List<Long> findListHasModuleVo(Long roleId) {
        if (roleId == null) return Collections.emptyList();
        String sql = "SELECT a.module_id FROM t_role_module a WHERE a.role_id = " + roleId;
        if (this.getCountSql(sql) == 0) {
            return Collections.emptyList();
        }
        Query query = em.createNativeQuery(sql);
        List<BigInteger> moduleIds = query.getResultList();
        List<Long> list = new ArrayList<Long>();

        for (BigInteger id : moduleIds) {
            list.add(id.longValue());
        }
        return list;
    }

    @Override
    public List<ModuleVo> findListModuleByUserId(Long userId) {
        List<Long> roleIds = null;
        String sql = "SELECT a.role_id FROM t_admin_role a WHERE a.admin_id = " + userId;
        Query query = em.createNativeQuery(sql);
        List<BigInteger> roleIdList = query.getResultList();
        String inParams = "";
        if (roleIdList != null && !roleIdList.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            for (BigInteger id : roleIdList) {
                buffer.append(String.valueOf(id)).append(",");
            }
            inParams = buffer.substring(0, buffer.length() - 1);
            String sql2 = "SELECT DISTINCT(rm.module_id) FROM t_role_module rm WHERE rm.role_id IN (" + inParams + ")";

            Query query2 = em.createNativeQuery(sql2);
            List<BigInteger> moduleIds = query2.getResultList();
            List<ModuleVo> moduleList = findInModuelIds(moduleIds);
            return moduleList;
        }
        return null;
    }

    @Override
    public List<ModuleVo> findInModuelIds(List<BigInteger> moduleIds) {

        List<Module> moduleList = null;
        if(moduleIds != null && !moduleIds.isEmpty()) {
            com.leoman.common.service.Query query = com.leoman.common.service.Query.forClass(Module.class, this);
            query.in("id", moduleIds);
            moduleList = this.queryAll(query);
        }
        else {
            moduleList = this.queryAll();
        }

        List<ModuleVo> allModule = new ArrayList<ModuleVo>();
        Map<Long, ModuleVo> topModule = new HashMap<Long, ModuleVo>();
        for (Module moduel : moduleList) {
            Module superModule = moduel.getSuperModule();
            if (superModule != null) {
                if (topModule.get(superModule.getId()) == null) {
                    ModuleVo moduleVo = new ModuleVo();
                    moduleVo.setId(superModule.getId());
                    moduleVo.setName(superModule.getName());
                    moduleVo.setModuleIcon(superModule.getModuleIcon());
                    moduleVo.addModule(moduel);
                    topModule.put(superModule.getId(), moduleVo);
                } else {
                    topModule.get(superModule.getId()).addModule(moduel);
                }
            }
        }
        for (Map.Entry<Long, ModuleVo> moduleVoEntry : topModule.entrySet()) {
            allModule.add(moduleVoEntry.getValue());
        }
        return allModule;
    }


    public List<ModuleVo> entityToVo(List<Module> list) {
        List<ModuleVo> voList = new ArrayList<ModuleVo>();
        ModuleVo vo = null;
        for (Module module : list) {
            vo = new ModuleVo();
            vo.setId(module.getId());
            vo.setName(module.getName());
            List<Module> list2 = this.queryByProperty("superModule.id", module.getId());
            List<ModuleVo> voList2 = null;
            if (list2 != null) {
                voList2 = new ArrayList<ModuleVo>();
                ModuleVo vo2 = null;
                for (Module module1 : list2) {
                    vo2 = new ModuleVo();
                    vo2.setId(module1.getId());
                    vo2.setName(module1.getName());
                    voList2.add(vo2);
                }
            }
            vo.setList(voList2);
            voList.add(vo);
        }
        return voList;
    }

    public void create(Module module, Long checkId) {
        int code = 0;
        if (checkId == null) {
            // 查找编号最后的模块编号
            code = findLastCode();
        } else {
            // 查找父模块最后模块编号
            code = findLastCodeByCheckId(checkId);
        }
        module.setCode(code);
        this.save(module);
    }

    private int findLastCodeByCheckId(Long checkId) {
        String sql = "SELECT a.code FROM t_module a WHERE a.pid IS NOT NULL AND a.pid = " + checkId + " ORDER BY a.id DESC LIMIT 1";
        Query query = em.createNativeQuery(sql);
        Integer count = null;
        try {
            count = this.getCountSql(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer code = null;
        if (count > 0) {
            code = (Integer) query.getSingleResult();
        }
        if (code == null) {
            Module module = this.queryByPK(checkId);
            code = module.getCode() + 1;
        } else {
            code += 1;
        }
        return code;
    }

    private int findLastCode() {
        String sql = "SELECT a.code FROM t_module a WHERE a.pid IS NULL ORDER BY a.id DESC LIMIT 1";

        Integer count = this.getCountSql(sql);
        if (count == null || count == 0) {
            return 1000;
        }
        Query query = em.createNativeQuery(sql);
        Integer code = (Integer) query.getSingleResult();
        if (code == null) {
            code = 1000;
        } else {
            code += 1000;
        }
        return code;
    }
}
