package com.leoman.permissions.admin.controller;

import com.leoman.entity.Constant;
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
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.system.enterprise.service.EnterpriseService;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import com.leoman.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private UserService userService;



    /**
     * 列表页面
     */
    @RequestMapping(value = "/index")
    public String index(Model model,HttpServletRequest request) {
        Admin admin = adminService.findByUsername("admin");
        model.addAttribute("admin", admin);
        UserInfo userInfo = this.getUser(request);
        model.addAttribute("userInfo",userInfo);
        return "permissions/admin/list2";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(String username, Integer draw, Integer start, Integer length ,Long enterpriseId) {
        int pagenum = getPageNum(start, length);

        Query query = Query.forClass(Admin.class, adminService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        query.like("username", username);
        query.eq("enterpriseId",enterpriseId);
        Page<Admin> page = adminService.queryPage(query);

        List<Admin> list = page.getContent();
        for(Admin a : list){
            String name = roleService.findName(a.getId());
            if(StringUtils.isNotBlank(name)){
                a.setRoleName(name);
            }else {
                a.setRoleName("");
            }
            if(a.getEnterpriseId() != null){
                Enterprise enterprise = enterpriseService.queryByPK(a.getEnterpriseId());
                a.setEnterprise(enterprise);
            }
        }
        return DataTableFactory.fitting(draw, page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Long id, Model model,HttpServletRequest request) {
        Admin admin = new Admin() ;
        if (id != null) {
            admin = adminService.queryByPK(id);
            model.addAttribute("admin", admin);
            List<AdminRole> adminRoles = adminRoleService.queryByProperty("adminId",id);
            if(!adminRoles.isEmpty() && adminRoles.size()>0){
                model.addAttribute("roleId",adminRoles.get(0).getRoleId());
            }
        }
        //角色表
        model.addAttribute("role",roleService.queryAll());
        //企业表
        model.addAttribute("enterprise",enterpriseService.queryAll());

        UserInfo userInfo = this.getUser(request);
        model.addAttribute("userInfo",userInfo);

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
    public Result save(Admin admin,Long enterprise_id, Long role_id) {
        return adminService.save(admin,enterprise_id,role_id);
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

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Integer del(Long id,String ids) {
        if (id==null && StringUtils.isBlank(ids)){
            return 1;
        }
        try {
            if(id!=null){
                if(id==1){
                    return 2;
                }
                adminService.delete(adminService.queryByPK(id));
                List<AdminRole> adminRoles = adminRoleService.queryByProperty("adminId",id);
                if(!adminRoles.isEmpty() && adminRoles.size()>0){
                    for(AdminRole a : adminRoles){
                        adminRoleService.delete(a);
                    }
                }
            }else {
                Long[] ss = JsonUtil.json2Obj(ids,Long[].class);
                for (Long _id : ss) {
                    if(_id==1){
                        return 2;
                    }
                    adminService.delete(adminService.queryByPK(_id));
                    List<AdminRole> adminRoles = adminRoleService.queryByProperty("adminId",_id);
                    if(!adminRoles.isEmpty() && adminRoles.size()>0){
                        for(AdminRole a : adminRoles){
                            adminRoleService.delete(a);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }


    /**
     * 重置密码
     * @param id
     * @return
     */
    @RequestMapping(value = "/reset")
    @ResponseBody
    public Result reset(Long id){
        try{
            Admin admin = adminService.queryByPK(id);
            admin.setPassword(MD5Util.MD5Encode("888888","UTF-8"));
            adminService.save(admin);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }


    public UserInfo getUser(HttpServletRequest request){
        Admin admin = (Admin) request.getSession().getAttribute(Constant.SESSION_MEMBER_GLOBLE);
        return userService.findByMobile(admin.getMobile());
    }

}
