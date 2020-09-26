package com.zilong.service.purchaseService;

import com.zilong.vo.PageVo;
import com.zilong.vo.purchase.SupplierCommodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierCommodityService {
    /**
     * 根据SupplierCommodity条件模糊查询多条数据方法
     */
    PageVo<SupplierCommodity> queryLike(SupplierCommodity suppliercommodity,int tid, int startIndex, int pageSize);

    /**
     * 根据采购订单查询供货商商品
     */
    PageVo<SupplierCommodity> querySupplierCommodityByPid(int pid, int startIndex, int pageSize);

    // 根据商品id和订单id差商品数量
    int queryCountByCidOrPid(int cid, int pid);
}
