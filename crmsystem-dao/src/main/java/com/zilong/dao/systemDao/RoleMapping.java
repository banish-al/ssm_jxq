package com.zilong.dao.systemDao;

import com.zilong.vo.systemVo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//** custom import **//
  //在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface RoleMapping {
  /**
   *根据用户id查询用户角色
   */
  List<Role> queryRoleByUid(int uid);

  /**
   *查询所以角色表
   */
  List<Role> queryAll();

  //添加一个角色
  int addRole(@Param("roleName") String roleName);
}