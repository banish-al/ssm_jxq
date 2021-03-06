package com.zilong.dao.purchaseDao;

import com.zilong.vo.purchase.SupplierCommodity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//** custom import **//
  //在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface SupplierCommodityMapping {

//** custom methods **//
  //在这里写你的自定义接口,每次生成.java文件都会保留这一段代码


//** /custom methods **//

  /**
   * 查询所有方法 
   */
  List<SupplierCommodity> queryAll();

  /**
   * 根据主键supplier_commodity_id(SupplierCommodity.supplierCommodityId)查询单条数据方法 
   */
  SupplierCommodity queryById(int id);

  /**
   * 根据SupplierCommodity条件查询多条数据方法 
   */
  List<SupplierCommodity> query(SupplierCommodity suppliercommodity);

  /**
   * 根据SupplierCommodity条件模糊查询多条数据方法 
   */
  List<SupplierCommodity> queryLike(@Param("suppliercommodity") SupplierCommodity suppliercommodity,@Param("tid") int tid);

  /**
   * 根据SupplierCommodity条件查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 
   */
  List<SupplierCommodity> queryLimit(@Param("SupplierCommodity") SupplierCommodity suppliercommodity, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

  /**
   * 根据SupplierCommodity条件模糊查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 
   */
  List<SupplierCommodity> queryLikeLimit(@Param("SupplierCommodity") SupplierCommodity suppliercommodity, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

  /**
   * 根据SupplierCommodity条件获取数据总条数方法 
   */
  int queryCount(SupplierCommodity suppliercommodity);

  /**
   * 根据SupplierCommodity模糊查询数据总条数方法 
   */
  int queryLikeCount(@Param("suppliercommodity") SupplierCommodity suppliercommodity,@Param("tid") int tid);

  /**
   * 根据SupplierCommodity插入数据方法 
   */
  int insert(SupplierCommodity suppliercommodity);

  /**
   * 根据SupplierCommodity条件修改单条数据方法,从传入对象获取id 
   */
  int updateById(SupplierCommodity suppliercommodity);

  /**
   * 根据SetValue条件修改多条数据方法,从传入对象Condition中获取修改的条件<br/>注意:如果Condition为空或Condition所有关键字段为空,则会修改该表所有数据 
   */
  int updates(@Param("SetValue") SupplierCommodity setValue, @Param("Condition") SupplierCommodity condition);

  /**
   * 根据SupplierCommodity条件删除单条数据方法,从传入对象获取id 
   */
  int deleteById(int id);

  /**
   * 根据SupplierCommodity条件修改多条数据方法,从传入对象获取删除条件<br/>注意:如果参数对象所有关键字段都为空值,则不会删除任何数据 
   */
  int deletes(SupplierCommodity suppliercommodity);

  /**
   * 根据采购订单查询供货商商品
   */
  List<SupplierCommodity> querySupplierCommodityByPid(int pid);

  /**
   * 根据采购订单查询供货商商品数量
   */
  int querySupplierCommodityByPidCount(int pid);

  // 根据商品id和订单id查商品数量
  int queryCountByCidOrPid(@Param("cid") int cid,@Param("pid") int pid);

  //根据商品id返回商品价格
  float queryPriceByCid(int cid);

  //根据采购订单id获取所有商品信息
  List<SupplierCommodity> querySupplierCommodityByPurchaseorferId(int purchaseorferId);
}