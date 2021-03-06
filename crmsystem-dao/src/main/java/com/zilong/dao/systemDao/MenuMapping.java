package com.zilong.dao.systemDao;

import com.zilong.vo.systemVo.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//** custom import **//
  //在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface MenuMapping {
    //根据父id,查询当前用户可操作的菜单
    List<Menu> queryMenuByPid(int pid);

    //根据角色id查询所已有的菜单id
    List<Menu> queryMenuByRid(int roleId);

    // 给角色添加菜单
    int addMenuByRole(@Param("roleId") int roleId,@Param("menuId") int menuId);

    // 根据角色id删除该角色权限
    int delMenuByRole(int roleId);
}