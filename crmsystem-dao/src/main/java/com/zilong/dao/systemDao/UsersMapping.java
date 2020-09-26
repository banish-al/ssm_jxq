package com.zilong.dao.systemDao;

import com.zilong.vo.systemVo.Menu;
import com.zilong.vo.systemVo.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//** custom import **//
  //在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface UsersMapping {

//** custom methods **//
  //在这里写你的自定义接口,每次生成.java文件都会保留这一段代码


//** /custom methods **//

  List<Menu> queryMenuByUid(@Param("uid") int uid,@Param("pid") int pid);

  List<Menu> queryMenuAll(int pid);

  List<Menu> queryMenuFristByUid(int uid);



  /**
   * 根据Users条件查询单条数据方法
   */
  Users queryByVo(Users users);

  /**
   * 查询所有方法 
   */
  List<Users> queryAll();

  /**
   * 根据主键user_id(Users.userId)查询单条数据方法 
   */
  Users queryById(int id);

  /**
   * 根据Users条件查询多条数据方法 
   */
  List<Users> query(Users users);

  /**
   * 根据Users条件模糊查询多条数据方法 
   */
  List<Users> queryLike(@Param("users") Users users,@Param("roleId") int roleId);

  /**
   * 根据Users条件查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 
   */
  List<Users> queryLimit(@Param("Users") Users users, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

  /**
   * 根据Users条件模糊查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 
   */
  List<Users> queryLikeLimit(@Param("Users") Users users, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

  /**
   * 根据Users条件获取数据总条数方法 
   */
  int queryCount(Users users);

  /**
   * 根据Users模糊查询数据总条数方法 
   */
  int queryLikeCount(@Param("users") Users users,@Param("roleId") int roleId);

  /**
   * 根据Users插入数据方法 
   */
  int insert(Users users);

  /**
   * 根据Users条件修改单条数据方法,从传入对象获取id 
   */
  int updateById(Users users);

  /**
   * 根据SetValue条件修改多条数据方法,从传入对象Condition中获取修改的条件<br/>注意:如果Condition为空或Condition所有关键字段为空,则会修改该表所有数据 
   */
  int updates(@Param("SetValue") Users setValue, @Param("Condition") Users condition);

  /**
   * 根据Users条件删除单条数据方法,从传入对象获取id 
   */
  int deleteById(int id);

  /**
   * 根据Users条件修改多条数据方法,从传入对象获取删除条件<br/>注意:如果参数对象所有关键字段都为空值,则不会删除任何数据 
   */
  int deletes(Users users);

  // 查询用户编号
  String userNumber();

  // 给角色添加角色
  int addRoleAsUser(@Param("userId")int userId,@Param("roleId")int roleId);

  // 根据员工id删除他的角色
  int delUserRoleByUserId(int userId);

  // 给用户添加角色
  int addUser_role(@Param("userId")int userId,@Param("roleId")int roleId);

}