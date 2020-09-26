package com.zilong.dao.salesDao;

import com.zilong.vo.salesVo.Salesticket;
import com.zilong.vo.systemVo.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//** custom import **//
  //在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface SalesticketMapping {

//** custom methods **//
  //在这里写你的自定义接口,每次生成.java文件都会保留这一段代码


//** /custom methods **//

  /**
   * 查询所有方法 
   */
  List<Salesticket> queryAll();

  /**
   * 根据Salesticket条件模糊查询多条数据方法 
   */
  List<Salesticket> queryLike(Salesticket salesticket);

  /**
   * 根据Salesticket模糊查询数据总条数方法 
   */
  int queryLikeCount(Salesticket salesticket);

  /**
   * 根据Salesticket插入数据方法 
   */
  int insert(Salesticket salesticket);

  // 根据销售订单id查询审核人
  String queryUserBySalesorder(int sid);

  //根据商品id和采购订单id查询商品数量
  int queryCountByCidOrSid(@Param("cid") int cid,@Param("sid") int sid);

  //出库成功，改变销售单状态
  int uptSalesticketChukuOk(int salesorderId);

  // 增加用户的交易次数,根据销售订单
  int addConnectionCountBySid(int sid);
}