package com.zilong.dao.dataDao;

import com.zilong.vo.dataVo.Supplier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//** custom import **//
  //在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface SupplierMapping {

//** custom methods **//
  //在这里写你的自定义接口,每次生成.java文件都会保留这一段代码


//** /custom methods **//

  /**
   * 查询所有方法 
   */
  List<Supplier> queryAll();

  /**
   * 根据主键supplier_id(Supplier.supplierId)查询单条数据方法 
   */
  Supplier queryById(int id);

  /**
   * 根据Supplier条件查询多条数据方法 
   */
  List<Supplier> query(Supplier supplier);

  /**
   * 根据Supplier条件模糊查询多条数据方法 
   */
  List<Supplier> queryLike(Supplier supplier);

  /**
   * 根据Supplier条件查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 
   */
  List<Supplier> queryLimit(@Param("Supplier") Supplier supplier, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

  /**
   * 根据Supplier条件模糊查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 
   */
  List<Supplier> queryLikeLimit(@Param("Supplier") Supplier supplier, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

  /**
   * 根据Supplier条件获取数据总条数方法 
   */
  int queryCount(Supplier supplier);

  /**
   * 根据Supplier模糊查询数据总条数方法 
   */
  int queryLikeCount(Supplier supplier);

  /**
   * 根据Supplier插入数据方法 
   */
  int insert(Supplier supplier);

  /**
   * 根据Supplier条件修改单条数据方法,从传入对象获取id 
   */
  int updateById(Supplier supplier);

  /**
   * 根据SetValue条件修改多条数据方法,从传入对象Condition中获取修改的条件<br/>注意:如果Condition为空或Condition所有关键字段为空,则会修改该表所有数据 
   */
  int updates(@Param("SetValue") Supplier setValue, @Param("Condition") Supplier condition);

  /**
   * 根据Supplier条件删除单条数据方法,从传入对象获取id 
   */
  int deleteById(int id);

  /**
   * 根据Supplier条件修改多条数据方法,从传入对象获取删除条件<br/>注意:如果参数对象所有关键字段都为空值,则不会删除任何数据 
   */
  int deletes(Supplier supplier);
}