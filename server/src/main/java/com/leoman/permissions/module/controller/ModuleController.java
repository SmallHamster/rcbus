package com.leoman.permissions.module.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.permissions.module.entity.Module;
import com.leoman.permissions.module.service.ModuleService;
import com.leoman.permissions.module.service.impl.ModuleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/29.
 */
@Controller
@RequestMapping(value = "/admin/module")
public class ModuleController extends GenericEntityController<Module, Module, ModuleServiceImpl> {

    @Autowired
    private ModuleService moduleService;

    /**
     * 模块首页
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/permissions/module/list";
    }

    /**
     * 根据条件查询模块列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(String name, Integer draw, Integer start, Integer length) {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(Module.class, moduleService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        query.like("name", name);
        query.addOrder("code","asc");
        Page<Module> page = moduleService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    /**
     * 跳转新增页面
     * @param id
     * @param checkId
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Long id, Long checkId ,Model model) {

        String fModuleName = "";
        if(id != null) {
            Module module = moduleService.queryByPK(id);
            model.addAttribute("module", module);
            model.addAttribute("fModuleName",module.getSuperModule() == null ? "" :module.getSuperModule().getName());
        }
        else {
            if(checkId != null) {
                Module module = moduleService.queryByPK(checkId);
                if(module != null) {
                    fModuleName = module.getName();
                }
            }
            model.addAttribute("fModuleName",fModuleName);
        }
        model.addAttribute("pid", checkId);
        return "/permissions/module/add";
    }

    /**
     * 保存模块
     * @param module
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Module module,Long pid) {
        try {
            if(pid != null) {
                module.setSuperModule(moduleService.queryByPK(pid));
            }
            moduleService.saveModule(module,pid);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }

    /**
     * 删除模块
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(Long id) {
        moduleService.deleteModules(id);
        return Result.success();
    }
}
