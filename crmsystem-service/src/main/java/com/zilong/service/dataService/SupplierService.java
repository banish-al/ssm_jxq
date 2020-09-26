package com.zilong.service.dataService;

import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Supplier;

import java.util.List;

public interface SupplierService {
    /**
     * 根据Supplier条件模糊查询多条数据方法
     */
    PageVo<Supplier> queryAllSupplier(Supplier supplier, int startIndex, int pageSize);

    /**
     * 根据Supplier插入数据方法
     */
    int insert(Supplier supplier);

    int update(Supplier supplier);
}
