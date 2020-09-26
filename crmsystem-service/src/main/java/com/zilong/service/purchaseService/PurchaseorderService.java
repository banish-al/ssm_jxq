package com.zilong.service.purchaseService;

import com.zilong.vo.PageVo;
import com.zilong.vo.purchase.Purchaseorder;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PurchaseorderService {
    /**
     * 根据Purchaseorder条件模糊查询多条数据方法
     */
    PageVo<Purchaseorder> queryLike(Purchaseorder purchaseorder,int startIndex, int pageSize);

    /**
     * 确认采购，添加采购信息，添加采购详情信息
     */
    int caigouOk(String commoditys, String counts, String text, HttpSession session);

    String queryPurchaseorderTextByPid(int pid);

    //根据id删除订单详情
    int delPurchaseorderDetailsByCid(String dis);

    // 根据订单详情id和商品id修改商品数量，根据订单id修改订单备注
    int uptPurchaseorderDetailsByOr(String ids,String counts,String text,int pid);

    //同意采购订单 生成采购单
    public int purchaseorderOk(int purchaseorferId);
    // 拒绝采购订单
    int turnPurchaserorder(int pid);
}
