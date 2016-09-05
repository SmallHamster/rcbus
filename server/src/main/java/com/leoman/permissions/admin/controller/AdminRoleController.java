//package com.leoman.permissions.admin.controller;
//
//import com.leoman.permissions.admin.entity.AdminRole;
//import com.leoman.permissions.admin.service.AdminRoleService;
//import com.leoman.permissions.admin.service.AdminService;
//import com.leoman.permissions.admin.service.impl.AdminRoleServiceImpl;
//import com.leoman.common.controller.common.GenericEntityController;
//import com.leoman.common.factory.DataTableFactory;
//import com.leoman.role.entity.Role;
//import com.leoman.utils.JsonUtil;
//import com.leoman.utils.WebUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Administrator on 2016/7/18 0018.
// */
//@Controller
//@RequestMapping(value = "admin/adminRole")
//public class AdminRoleController extends GenericEntityController<AdminRole, AdminRole, AdminRoleServiceImpl> {
//
//    @Autowired
//    private AdminService adminService;
//
//    @Autowired
//    private AdminRoleService adminRoleService;
//
//    @Autowired
//    private RoleService roleService;
//
//    @RequestMapping("index")
//    public String index() {
//        return "adminrole/list";
//    }
//
//    @RequestMapping("/list")
//    public void list(HttpServletResponse response,
//                     String account,
//                     Long roleId,
//                     String mobile,
//                     Integer draw,
//                     Integer start,
//                     Integer length) {
//        if (null == start || start == 0) {
//            start = 1;
//        }
//        int pageNum = getPageNum(start, length);
//        adminRoleService = null;
//        Page<AdminRole> page = adminRoleService.page(account, roleId, mobile, pageNum, length);
//
//        Map<String, Object> result = DataTableFactory.fitting(draw, page);
//        WebUtil.printJson(response, result);
//    }
//
//    @RequestMapping("/add")
//    public String add(ModelMap model, Long id) {
//        if (id != null) {
//            AdminRole adminRole = adminRoleService.queryByPK(id);
//            model.addAttribute("adminRole", adminRole);
//        }
//        return "adminrole/add";
//    }
//
//    @RequestMapping("/save")
//    @ResponseBody
//    public Integer save(HttpServletRequest request,
//                        Long id,
//                        Long roleId,
//                        String account,
//                        String mobile,
//                        String password) {
//        /*try {
//
//            AdminRole adminRole = null;
//
//            if (null == id) {
//                adminRole = new AdminRole();
//                Admin admin = new Admin();
//                admin.setCreateDate(System.currentTimeMillis());
//                admin.setUsername(account);
//                admin.setMobile(mobile);
//                admin.setPassword(Md5Util.md5(password));
//                adminService.save(admin);
//
//                adminRole.setRole(roleService.queryByPK(roleId));
//                adminRole.setAdmin(admin);
//                adminRole.setCreateDate(System.currentTimeMillis());
//                adminRole.setUpdateDate(System.currentTimeMillis());
//                adminRoleService.insert(adminRole);
//            } else {
//                adminRole = adminRoleService.queryByPK(id);
//                adminRole.getAdmin().setUsername(account);
//                adminRole.getAdmin().setMobile(mobile);
//                adminRole.getAdmin().setPassword(Md5Util.md5(password));
//                adminRole.setRole(roleService.queryByPK(roleId));
//                adminRole.setUpdateDate(System.currentTimeMillis());
//                adminRoleService.update(adminRole);
//            }
//
//            return 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;*/
//        return adminRoleService.save(request, id, roleId, account, mobile, password);
//    }
//
//    @RequestMapping("delete")
//    @ResponseBody
//    public Integer delete(HttpServletRequest request, Long id) {
//        if (id != null) {
//            adminRoleService.delete(adminRoleService.queryByPK(id));
//            return 1;
//        }
//        return 0;
//    }
//
//    @RequestMapping("/deleteBatch")
//    @ResponseBody
//    public Integer deleteBatch(HttpServletRequest request, String ids) {
//        try {
//            Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
//            for (Long id : arrayId) {
//                adminRoleService.delete(adminRoleService.queryByPK(id));
//            }
//
//            return 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    @RequestMapping("/roleList")
//    @ResponseBody
//    public List<Role> roleList() {
//        List<Role> roleList = roleService.queryAll();
//        return roleList;
//    }
//
//    @RequestMapping("/changePassword")
//    @ResponseBody
//    public Integer changePassword(Long userId, String password) {
//        AdminRole adminRole = adminRoleService.queryByPK(userId);
//        adminRole.getAdmin().setPassword(password);
//        adminService.update(adminRole.getAdmin());
//
//        return 1;
//    }
//}
