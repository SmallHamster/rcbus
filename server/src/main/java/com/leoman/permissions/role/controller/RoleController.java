package com.leoman.permissions.role.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.permissions.module.entity.vo.ModuleVo;
import com.leoman.permissions.module.service.ModuleService;
import com.leoman.permissions.role.entity.Role;
import com.leoman.permissions.role.service.RoleService;
import com.leoman.permissions.role.service.impl.RoleServiceImpl;
import com.leoman.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yesong on 2016/8/26.
 */
@Controller
@RequestMapping(value = "/admin/role")
public class RoleController extends GenericEntityController<Role, Role, RoleServiceImpl> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "permissions/role/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(String name, Integer draw, Integer start, Integer length) {

        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(Role.class, roleService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        query.like("name", name);
        Page<Role> page = roleService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Long id, Model model) {
        if(id != null) {
            model.addAttribute("role",roleService.queryByPK(id));
        }
        try {
            List<ModuleVo> voList = moduleService.findListModuleVo();
            model.addAttribute("modules",voList);
            List<Long> moduleIds = moduleService.findListHasModuleVo(id);
            model.addAttribute("subModules",moduleIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "permissions/role/add";
    }

    /**
     * 保存
     * @param role
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Role role,String moduleIds) {

        String[] moduleIdss = moduleIds.split(",");
        try {
            roleService.saveRole(role,moduleIdss);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }

    /**
     * 角色详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Long id, Model model) {
        model.addAttribute("role", roleService.queryByPK(id));
        return "permissions/role/detail";
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result detele(Long id) {
        try {
            roleService.delete(roleService.queryByPK(id));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }
}
