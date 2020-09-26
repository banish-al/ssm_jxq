package com.zilong.service.salesorderService;

import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.salesVo.Salesorder;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface SalesorderService {
    PageVo<Salesorder> querySalesorder(Salesorder salesorder,int startIndex,int pageSize);

    //生成销售订单
    int addsalesorder(String commoditys, String counts,String text, HttpSession session);

    //根据销售订单查询商品信息
    List<Commodity> queryCommodityBySid(int sid);
    //根据商品id和采购订单id查询商品数量
    int queryCountByCidOrSid(int cid,int sid);
    // 拒接采购订单
    int noSalesorderStateById(int salesorderId);
}
