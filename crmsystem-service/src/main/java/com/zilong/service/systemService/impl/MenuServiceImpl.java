package com.zilong.service.systemService.impl;

import com.zilong.dao.systemDao.MenuMapping;
import com.zilong.dao.systemDao.UsersMapping;
import com.zilong.service.systemService.MenuService;
import com.zilong.vo.systemVo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapping menuMapping;

    @Autowired
    UsersMapping usersMapping;

    @Override
    public List<Menu> queryAllMenu(int roleId) {
        // 查询角色所拥有的菜单id
        List<Menu> roleMenus = menuMapping.queryMenuByRid(roleId);

        //获取所有的父菜单   需要返回出去的数据
        List<Menu> parentMenus = menuMapping.queryMenuByPid(0);

        //循环  每一个父菜单 根据id，，查询出所有的子菜单，再根据角色id，如果已有菜单就选中此菜单
        for (Menu pMenus:parentMenus){
            //根据父id查询出所有子菜单数据，赋值给父菜单id里面的Children属性
            List<Menu> childmenus = menuMapping.queryMenuByPid(pMenus.getId());

            //循环  查询出子菜单信息，有就选中
            for(Menu child : childmenus){
                //循环权限菜单，根据角色id，如果有就选中
                for(Menu rMenu:roleMenus){
                    if(child.getId() ==rMenu.getId()){  //子菜单id  == 权限表 菜单id
                        child.setChecked(true);
                        pMenus.setState("open");
                        break;
                    }
                }
            }
            pMenus.setChildren(childmenus);
        }
        return parentMenus;
    }

    @Override
    public int addMenuByRole(int roleId, String menuIds) {
        int i = 1;
        String [] mids = menuIds.split(",");
        for (String mid: mids) {
            // 如果没有设置权限，则跳出循环
            try {
                int menuId = Integer.valueOf(mid);
                if(menuId >10){
                    i *=menuMapping.addMenuByRole(roleId,menuId);
                }
            }catch (Exception e){
                break;
            }
        }

        if(i == 1){
            return 1;
        }
        return 0;
    }

    // 根据角色id删除该角色权限
    public int delMenuByRole(int roleId){
        return menuMapping.delMenuByRole(roleId);
    }
}
