package com.zilong.service.purchaseService;

import com.zilong.vo.PageVo;
import com.zilong.vo.purchase.Purchasenote;

import javax.servlet.http.HttpSession;

public interface PurchasenoteService {

    PageVo<Purchasenote> queryPurchasenote(Purchasenote purchasenote, int startIndex, int pageSize);

    // 根据采购订单id生成采购单
    int addPurchasenote(int purchaseorderId, HttpSession session);

    // 入库
    int rukuOk(int purchaseorderId,int warehouseId);
}
