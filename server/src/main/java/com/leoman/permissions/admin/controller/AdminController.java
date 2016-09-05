package com.leoman.permissions.admin.controller;

import com.leoman.pay.util.MD5Util;
import com.leoman.permissions.admin.entity.Admin;
import com.leoman.permissions.admin.service.impl.AdminServiceImpl;
import com.leoman.permissions.admin.service.AdminService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.permissions.adminrole.entity.AdminRole;
import com.leoman.permissions.adminrole.service.AdminRoleService;
import com.leoman.permissions.role.entity.Role;
import com.leoman.permissions.role.entity.vo.RoleSelectVo;
import com.leoman.permissions.role.service.RoleService;
import com.leoman.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/5 0005.
 */
@Controller
@RequestMapping(value = "/admin/admin")
public class AdminController extends GenericEntityController<Admin, Admin, AdminServiceImpl> {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminRoleService adminRoleService;

    /**
     * 列表页面
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        Admin admin = adminService.findByUsername("admin");
        model.addAttribute("admin", admin);
        return "permissions/admin/list2";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(String username, Integer draw, Integer start, Integer length) {
        int pagenum = getPageNum(start, length);

        Query query = Query.forClass(Admin.class, adminService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        query.like("username", username);
        Page<Admin> page = adminService.queryPage(query);
//        try {
//            page =
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Page<Admin> page = adminService.page(pagenum,length);
        return DataTableFactory.fitting(draw, page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Long id, Model model) {
        if (id != null) {
            model.addAttribute("admin", adminService.queryByPK(id));
        }
        return "permissions/admin/add";
    }

    /**
     * 保存
     *
     * @param admin
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Admin admin) {
        admin.setLastLoginDate(System.currentTimeMillis());
        try {
            admin.setPassword(MD5Util.MD5Encode(admin.getPassword(),"UTF-8"));
            adminService.save(admin);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 验证用户名是否存在
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/check/username", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkUsername(String username, Long id) {

        if (id != null) return true;

        List<Admin> list = adminService.queryByProperty("username", username);
        if (list != null && !list.isEmpty()) {
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/role/select", method = RequestMethod.POST)
    @ResponseBody
    public Result roleSelect(Long adminId) {
        Admin admin = adminService.queryByPK(adminId);
        List<Role> allRoleList = roleService.queryAll();
        List<AdminRole> selectRoleList = adminRoleService.queryByProperty("adminId", admin.getId());
        return Result.success(packagingVo(allRoleList, selectRoleList));
    }

    public Map<String, List> packagingVo(List<Role> allRoleList, List<AdminRole> selectRoleList) {
        List<String> list = new ArrayList<String>();
        for (AdminRole adminRole : selectRoleList) {
            list.add(String.valueOf(adminRole.getRoleId()));
        }
        Map<String, List> map = new HashMap<String, List>();
        map.put("allRoles", allRoleList);
        map.put("hasRoels", list);
        return map;
    }

    /**
     * 保存选中的角色
     * @return
     */
    @RequestMapping(value = "/role/save", method = RequestMethod.POST)
    @ResponseBody
    public Result toRole(String roleIds, Long adminId) {

        // 先删除改管理员所有角色
        adminRoleService.deleteByAdminId(adminId);

        Long[] ids = JsonUtil.json2Obj(roleIds,Long[].class);
        AdminRole adminRole = null;
        List<AdminRole> list = new ArrayList<AdminRole>();
        for (Long id : ids) {
            adminRole = new AdminRole();
            adminRole.setRoleId(id);
            adminRole.setAdminId(adminId);
            list.add(adminRole);
        }
        try {
            adminRoleService.saveList(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }
}
