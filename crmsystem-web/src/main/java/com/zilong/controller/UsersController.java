package com.zilong.controller;

import com.zilong.dao.systemDao.UsersMapping;
import com.zilong.service.systemService.CitysService;
import com.zilong.service.systemService.MenuService;
import com.zilong.service.systemService.RoleService;
import com.zilong.service.systemService.UsersService;
import com.zilong.vo.systemVo.Menu;
import com.zilong.vo.PageVo;
import com.zilong.vo.systemVo.Role;
import com.zilong.vo.systemVo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UsersController {
    @Autowired
    UsersService usersService;

    @Autowired
    UsersMapping usersMapping;

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    /**
     * 查询所有职位
     */
    @RequestMapping("/queryAllRole.action")
    public List<Role> queryAllRole() {
        return roleService.queryAll();
    }

    //添加职位
    @RequestMapping(value = "/addRole.action", produces = "text/html;charset=UTF-8")
    public String addRole(String roleName) {
        List<Role> list = queryAllRole();
        int i = 1;
        for (Role role : list) {
            if (roleName.equals(role.getRoleName())) {
                i = 0;
                break;
            }
        }
        if (i == 0) {
            return "0";
        } else {
            int row = roleService.addRole(roleName);
            return row > 0 ? "添加成功" : "添加失败";
        }
    }

    @RequestMapping("/queryLikeUser.action")
    public PageVo<Users> test1(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "rows", defaultValue = "5") int rows,
                               @RequestParam(value = "roleId", defaultValue = "0") int roleId,
                               Users users) {
        PageVo<Users> pageVo = usersService.queryLike(users, page, rows, roleId);
        return pageVo;
    }

    /**
     * 首次登录,设置session
     */
    @RequestMapping(value = "/user_login.action")
    public int user_login(Users users, HttpServletRequest request) {
        System.out.println("用户名：" + users.getUserName() + "密码：" + users.getUserPassword());
        try {
            Users users1 = usersService.queryByVo(users);
            Integer userId = users1.getUserId();
            HttpSession session = request.getSession();
            session.setAttribute("user", users1);
            return userId;
        } catch (Exception e) {
            // 若失败，返回0，继续登录
            return 0;
        }
    }

    /**
     * 菜单栏
     */
    @RequestMapping(value = "/menu.action")
    public List<Menu> queryMenuByUid(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletRequest request) {
        List<Menu> list = null;
        HttpSession session = request.getSession();
        Users users = (Users) session.getAttribute("user");
        int uid = users.getUserId();
        if (id == 0) {
            //list = usersMapping.queryMenuAll(id);
            //第一次进入：根据用户id获取用户的权限的主菜单
            list = usersMapping.queryMenuFristByUid(uid);
        } else {
            //之后进入：根据用户id获取主菜单的子菜单
            list = usersMapping.queryMenuByUid(uid, id);
        }
        return list;
    }

    //给角色授权
    @RequestMapping("querymenus.action")
    public List<Menu> queryMenuByPid(@RequestParam(value = "roleId", defaultValue = "0") int roleId) {
        return menuService.queryAllMenu(roleId);
    }

    @Autowired
    CitysService citysService;

    // 添加员工
    @RequestMapping(value = "/addUser.action", produces = "text/html;charset=UTF-8")
    public String addUser(Users users,
                          @RequestParam("shen") String shen,
                          @RequestParam("shi") String shi,
                          @RequestParam("qu") String qu,
                          @RequestParam("roleId") int roleId) {

        String citys = "";
        if (shen != null) {
            citys += citysService.queryProvinceByCode(shen);
        }
        if (shi != null) {
            citys += "/" + citysService.queryCityByCode(shi);
        }
        if (qu != null) {
            citys += "/" + citysService.queryAreaByCode(qu);
        }
        users.setUserAddr(citys);
        users.setUserState("在职");

        int row = usersService.insert(users);
        if (row == 1) {
            int row2 = usersService.addRoleAsUser(users.getUserId(), roleId);
            if (row2 == 1) {
                return "添加成功";
            }
        }
        return "添加失败";
    }

    // 根据id查询员工信息
    @RequestMapping("/queryUserById.action")
    public Users queryUserById(@RequestParam("userId") int userId) {
        Users users = usersService.queryById(userId);
        return users;
    }

    //修改员工
    @RequestMapping(value = "/uptUser.action", produces = "text/html;charset=UTF-8")
    public String uptUser(Users users,
                          @RequestParam("shen") String shen,
                          @RequestParam("shi") String shi,
                          @RequestParam("qu") String qu) {
        String citys;
        try {
            Integer.valueOf(shen);
            citys = citysService.queryProvinceByCode(shen) + "/" +
                    citysService.queryCityByCode(shi) + "/" +
                    citysService.queryAreaByCode(qu);
        } catch (Exception e) {
            citys = shen + "/" + shi + "/" + qu;
        }
        users.setUserAddr(citys);
        int row = usersService.uptUserById(users);
        return row > 0 ? "修改成功" : "修改失败";
    }

    //给员工添加角色
    @RequestMapping(value = "/addUserRole.action", produces = "text/html;charset=UTF-8")
    public String addUser_role(@RequestParam("userId") int userId, @RequestParam("roleIds") String roleIds) {
        //先删除原有员工id的角色
        usersService.delUserRoleByUserId(userId);

        int i = 1;
        char[] chars = roleIds.toCharArray();
        for (char c : chars) {
            int num = Integer.parseInt(String.valueOf(c));
            int row = usersService.addUser_role(userId, num);
            i *= row;
        }
        if (i == 1) {
            return "授权成功";
        }
        return "授权失败";
    }

    //给角色授权
    @RequestMapping(value = "/addMenuByRid.action", produces = "text/html;charset=UTF-8")
    public String addMenuByRid(@RequestParam("roleId") int roleId, @RequestParam("menuIds") String menuIds) {
        //先删除原有角色的权限
        menuService.delMenuByRole(roleId);

        //给角色指定的权限
        int i = menuService.addMenuByRole(roleId, menuIds);
        if (i == 1) {
            return "授权成功";
        }
        return "授权失败";
    }
}