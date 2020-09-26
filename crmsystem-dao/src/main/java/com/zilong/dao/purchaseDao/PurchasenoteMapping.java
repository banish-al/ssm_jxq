package com.zilong.dao.purchaseDao;

import com.zilong.vo.purchase.Purchasenote;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//** custom import **//
  //在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface PurchasenoteMapping {

//** custom methods **//
  //在这里写你的自定义接口,每次生成.java文件都会保留这一段代码


//** /custom methods **//

  /**
   * 查询所有方法 
   */
  List<Purchasenote> queryAll();

  /**
   * 根据Purchasenote条件模糊查询多条数据方法 
   */
  List<Purchasenote> queryLike(Purchasenote purchasenote);


  /**
   * 根据Purchasenote模糊查询数据总条数方法 
   */
  int queryLikeCount(Purchasenote purchasenote);

  // 根据擦干订单添加采购单
  int addPurchasenote(Purchasenote purchasenote);

  // 设置成已入库
  int setyirukuBypid(int pid);
}