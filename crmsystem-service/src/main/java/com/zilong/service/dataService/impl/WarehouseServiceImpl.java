package com.zilong.service.dataService.impl;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.dataDao.SupplierMapping;
import com.zilong.dao.dataDao.WarehouseMapping;
import com.zilong.service.dataService.SupplierService;
import com.zilong.service.dataService.WarehouseService;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Supplier;
import com.zilong.vo.dataVo.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    WarehouseMapping warehouseMapping;


    @Override
    public PageVo<Warehouse> queryLikeWarehouse(Warehouse warehouse, int startIndex, int pageSize) {
        PageVo<Warehouse> pageVo = new PageVo<>();

        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(warehouseMapping.queryLike(warehouse));

        pageVo.setTotal(warehouseMapping.queryLikeCount(warehouse));

        return pageVo;
    }

    @Override
    public int uptWarehouse(Warehouse warehouse) {
        return warehouseMapping.updateById(warehouse);
    }

    @Override
    public int queryWarehouseCountByWid(int wid) {
        return warehouseMapping.queryWarehouseCountByWid(wid);
    }

    @Override
    public List<Warehouse> queryAllWarehouse() {
        return warehouseMapping.queryAll();
    }



}
