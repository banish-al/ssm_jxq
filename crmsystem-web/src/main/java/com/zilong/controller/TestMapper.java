package com.zilong.controller;

import com.zilong.dao.dataDao.CommodityMapping;
import com.zilong.dao.dataDao.SupplierMapping;
import com.zilong.dao.dataDao.WarehouseMapping;
import com.zilong.dao.purchaseDao.PurchasenoteMapping;
import com.zilong.dao.purchaseDao.SupplierCommodityMapping;
import com.zilong.dao.salesDao.SalesorderMapping;
import com.zilong.dao.salesDao.SalesticketMapping;
import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.purchase.Purchasenote;
import com.zilong.vo.purchase.SupplierCommodity;
import com.zilong.vo.salesVo.Salesorder;
import com.zilong.vo.salesVo.Salesticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestMapper {
    @Autowired
    WarehouseMapping warehouseMapping;

    @RequestMapping("/test1")
    public int test1() {
        System.out.println(warehouseMapping.existsByCidOrWarehouseId(1, 3));
        return warehouseMapping.existsByCidOrWarehouseId(1, 3);
    }
}
