package com.zilong.service.queryInventoryService;

import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;

public interface QueryInventoryService {
    PageVo<Commodity> queryInventoryByCommodity(Commodity commodity,int wid, int startIndex, int pageSize);

    // 查询一个仓库的最大容量
    int queryWarehouseMaxCount(int wid);

    //转库
    int turnInventory(int[] turnCommodityId, int[] turnCommodityCount, int turnwid, int wid);
}
