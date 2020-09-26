package com.zilong.dao.purchaseDao;

import com.zilong.vo.purchase.Purchaseorder;
import com.zilong.vo.purchase.SupplierCommodity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//** custom import **//
  //在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface PurchaseorderMapping {

//** custom methods **//
  //在这里写你的自定义接口,每次生成.java文件都会保留这一段代码


//** /custom methods **//

  /**
   * 查询所有方法 
   */
  List<Purchaseorder> queryAll();

  /**
   * 根据主键purchaseorder_id(Purchaseorder.purchaseorderId)查询单条数据方法 
   */
  Purchaseorder queryById(int id);

  /**
   * 根据Purchaseorder条件查询多条数据方法 
   */
  List<Purchaseorder> query(Purchaseorder purchaseorder);

  /**
   * 根据Purchaseorder条件模糊查询多条数据方法 
   */
  List<Purchaseorder> queryLike(Purchaseorder purchaseorder);

  /**
   * 根据Purchaseorder条件查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 
   */
  List<Purchaseorder> queryLimit(@Param("Purchaseorder") Purchaseorder purchaseorder, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

  /**
   * 根据Purchaseorder条件模糊查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数 
   */
  List<Purchaseorder> queryLikeLimit(@Param("Purchaseorder") Purchaseorder purchaseorder, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

  /**
   * 根据Purchaseorder条件获取数据总条数方法 
   */
  int queryCount(Purchaseorder purchaseorder);

  /**
   * 根据Purchaseorder模糊查询数据总条数方法 
   */
  int queryLikeCount(Purchaseorder purchaseorder);

  /**
   * 根据Purchaseorder插入数据方法 
   */
  int insert(Purchaseorder purchaseorder);

  /**
   * 根据Purchaseorder条件修改单条数据方法,从传入对象获取id 
   */
  int updateById(Purchaseorder purchaseorder);

  /**
   * 根据SetValue条件修改多条数据方法,从传入对象Condition中获取修改的条件<br/>注意:如果Condition为空或Condition所有关键字段为空,则会修改该表所有数据 
   */
  int updates(@Param("SetValue") Purchaseorder setValue, @Param("Condition") Purchaseorder condition);

  /**
   * 根据Purchaseorder条件删除单条数据方法,从传入对象获取id 
   */
  int deleteById(int id);

  /**
   * 根据Purchaseorder条件修改多条数据方法,从传入对象获取删除条件<br/>注意:如果参数对象所有关键字段都为空值,则不会删除任何数据 
   */
  int deletes(Purchaseorder purchaseorder);

  // 添加订单详情
  int addPurchaseorderDetails(@Param("pid") int pid,@Param("cid") int cid,@Param("count") int count);

  // 获取采购单备注
  String queryPurchaseorderTextByPid(int pid);

  //根据id删除订单详情
  int delPurchaseorderDetailsByCid(int cid);

  // 根据订单id和商品id修改商品数量
  int uptCountByCidOrPid(@Param("cid") int cid,@Param("pid") int pid,@Param("countId") int countId);

  // 根据订单id修改订单备注
  int uptTextByPid(@Param("pid") int pid,@Param("text") String text);

  // 修改订单详情的价格
  int uptPriceByPid(@Param("pid") int pid,@Param("price") float price);

  //根据采购单查询采购订单
  Purchaseorder queryPurchaseorderByPurchasenoteId(int purchaseorder_id);

  // 根据订单id查询该订单的总数
  int queryCountByPid(int pid);

  // 拒绝采购订单
  int turnPurchaserorder(int pid);

}