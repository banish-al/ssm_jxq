package com.zilong.service.systemService;

import com.zilong.vo.systemVo.Menu;
import com.zilong.vo.PageVo;
import com.zilong.vo.systemVo.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersService {

    /**
     * 根据主键user_id(Users.userId)查询单条数据方法
     */
    Users queryById(int id);

    /**
     * 根据Users条件查询多条数据方法
     */
    Users queryByVo(Users users);

    List<Menu> queryMenuByUid(@Param("uid") int uid, @Param("pid") int pid);

    List<Menu> queryMenuAll(int pid);

    List<Menu> queryMenuFristByUid(int uid);

    /**
     * 根据Users条件模糊查询多条数据方法
     */
    PageVo<Users> queryLike(Users users,int startIndex,int pageSize,int roleId);

    // 查询用户编号
    String userNumber();

    /**
     * 根据Users插入数据方法
     */
    int insert(Users users);

    // 给角色添加角色
    int addRoleAsUser(int userId,int roleId);

    // 修改员工
    int uptUserById(Users users);

    // 给用户添加角色
    int addUser_role(@Param("userId")int userId,@Param("roleId")int roleId);

    // 根据员工id删除他的角色
    int delUserRoleByUserId(int userId);
}
