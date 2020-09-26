package com.zilong.service.dataService.impl;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.dataDao.SupplierMapping;
import com.zilong.service.dataService.SupplierService;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierMapping supplierMapping;

    @Override
    public PageVo<Supplier> queryAllSupplier(Supplier supplier, int startIndex, int pageSize) {
        PageVo<Supplier> pageVo = new PageVo<>();

        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(supplierMapping.queryLike(supplier));

        pageVo.setTotal((supplierMapping.queryLikeCount(supplier)));
        return pageVo;
    }

    @Override
    public int insert(Supplier supplier) {
        return supplierMapping.insert(supplier);
    }

    @Override
    public int update(Supplier supplier) {
        return supplierMapping.updateById(supplier);
    }
}
