package com.zilong.service.systemService;

import com.zilong.vo.systemVo.Menu;

import java.util.List;

public interface MenuService {
    //根据父id,查询当前用户可操作的菜单
    List<Menu> queryAllMenu(int roleId);

    // 给角色添加菜单
    int addMenuByRole(int roleId, String menuIds);

    // 根据角色id删除该角色权限
    int delMenuByRole(int roleId);
}
