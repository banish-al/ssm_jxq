package com.zilong.dao.dataDao;

import com.zilong.vo.dataVo.Commoditytype;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//** custom import **//
  //在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface CommoditytypeMapping {

//** custom methods **//
  //在这里写你的自定义接口,每次生成.java文件都会保留这一段代码


//** /custom methods **//

  /**
   * 查询所有方法 
   */
  List<Commoditytype> queryAll();

  /**
   * 根据主键commoditytype_id(Commoditytype.commoditytypeId)查询单条数据方法 
   */
  Commoditytype queryById(int id);

  /**
   * 根据Commoditytype条件查询多条数据方法 
   */
  List<Commoditytype> query(Commoditytype commoditytype);

  /**
   * 根据Commoditytype条件模糊查询多条数据方法 
   */
  List<Commoditytype> queryLike(Commoditytype commoditytype);

  /**
   * 根据Commoditytype条件查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 
   */
  List<Commoditytype> queryLimit(@Param("Commoditytype") Commoditytype commoditytype, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

  /**
   * 根据Commoditytype条件模糊查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 
   */
  List<Commoditytype> queryLikeLimit(@Param("Commoditytype") Commoditytype commoditytype, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

  /**
   * 根据Commoditytype条件获取数据总条数方法 
   */
  int queryCount(Commoditytype commoditytype);

  /**
   * 根据Commoditytype模糊查询数据总条数方法 
   */
  int queryLikeCount(Commoditytype commoditytype);

  /**
   * 根据Commoditytype插入数据方法 
   */
  int insert(Commoditytype commoditytype);

  /**
   * 根据Commoditytype条件修改单条数据方法,从传入对象获取id 
   */
  int updateById(Commoditytype commoditytype);

  /**
   * 根据SetValue条件修改多条数据方法,从传入对象Condition中获取修改的条件<br/>注意:如果Condition为空或Condition所有关键字段为空,则会修改该表所有数据 
   */
  int updates(@Param("SetValue") Commoditytype setValue, @Param("Condition") Commoditytype condition);

  /**
   * 根据Commoditytype条件删除单条数据方法,从传入对象获取id 
   */
  int deleteById(int id);

  /**
   * 根据Commoditytype条件修改多条数据方法,从传入对象获取删除条件<br/>注意:如果参数对象所有关键字段都为空值,则不会删除任何数据 
   */
  int deletes(Commoditytype commoditytype);
}