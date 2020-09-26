package com.zilong.service.queryInventoryService.impl;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.dataDao.CommodityMapping;
import com.zilong.dao.dataDao.WarehouseMapping;
import com.zilong.service.queryInventoryService.QueryInventoryService;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.dataVo.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryInventoryServiceImpl implements QueryInventoryService {

    @Autowired
    CommodityMapping commodityMapping;

    @Autowired
    WarehouseMapping warehouseMapping;

    @Override
    public PageVo<Commodity> queryInventoryByCommodity(Commodity commodity, int wid, int startIndex, int pageSize) {
        PageVo<Commodity> pageVo = new PageVo<>();

        if (wid == 0) {
            wid = warehouseMapping.queryWarehouseByOne();
        }

        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(commodityMapping.queryCommodityByWid(commodity, wid));

        pageVo.setTotal(commodityMapping.queryCommodityByWidCount(commodity, wid));
        return pageVo;
    }

    @Override
    public int queryWarehouseMaxCount(int wid) {
        return warehouseMapping.queryWarehouseMaxCount(wid);
    }

    @Override
    public int turnInventory(int[] turnCommodityId, int[] turnCommodityCount, int turnwid, int wid) {
        int j = 1;
        for (int i = 0; i < turnCommodityId.length; i++) {
            int cid = turnCommodityId[i];
            int count = turnCommodityCount[i];
            // 如果count==0就不需要转库
            if (count != 0) {
                //先减少原来仓库的库存
                j *= warehouseMapping.lessenCidByTurnWarehouseId(cid,count,turnwid);

                //判断转到的仓库是否已有此商品
                if(warehouseMapping.existsByCidOrWarehouseId(cid,wid) > 0){
                    j *= warehouseMapping.setCidByWarehouseId(cid,count,wid);
                }else {
                    // 不存在就添加
                    j *= warehouseMapping.addCidByWarehouseId(cid,count,wid);
                }
            }
        }
        warehouseMapping.delCidByCountIsZero();
        return j;
    }
}
