package com.zilong.dao.dataDao;

import com.zilong.vo.dataVo.Connection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//** custom import **//
  //在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface ConnectionMapping {

//** custom methods **//
  //在这里写你的自定义接口,每次生成.java文件都会保留这一段代码


//** /custom methods **//

  /**
   * 查询所有方法 
   */
  List<Connection> queryAll();

  // 根据名字查询单个
  Connection queryByName(String name);

  /**
   * 根据主键connection_id(Connection.connectionId)查询单条数据方法 
   */
  Connection queryById(int connection_id);

  /**
   * 根据Connection条件模糊查询多条数据方法 
   */
  List<Connection> queryLike(Connection connection);

  /**
   * 根据Connection模糊查询数据总条数方法 
   */
  int queryLikeCount(Connection connection);

  /**
   * 根据Connection插入数据方法 
   */
  int insert(Connection connection);

  /**
   * 根据Connection条件修改单条数据方法,从传入对象获取id
   */
  int updateById(Connection connection);

  //删除客户，改变状态
  int delConnectionByCid(int Cid);
}