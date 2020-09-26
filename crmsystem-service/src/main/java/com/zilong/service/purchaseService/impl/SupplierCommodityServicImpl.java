package com.zilong.service.purchaseService.impl;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.purchaseDao.SupplierCommodityMapping;
import com.zilong.service.purchaseService.SupplierCommodityService;
import com.zilong.vo.PageVo;
import com.zilong.vo.purchase.SupplierCommodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierCommodityServicImpl implements SupplierCommodityService {

    @Autowired
    SupplierCommodityMapping supplierCommodityMapping;

    @Override
    public PageVo<SupplierCommodity> queryLike(SupplierCommodity suppliercommodity,int tid, int startIndex, int pageSize) {
        PageVo<SupplierCommodity> pageVo = new PageVo<>();

        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(supplierCommodityMapping.queryLike(suppliercommodity,tid));
        pageVo.setTotal(supplierCommodityMapping.queryLikeCount(suppliercommodity,tid));
        return pageVo;
    }

    @Override
    public PageVo<SupplierCommodity> querySupplierCommodityByPid(int pid, int startIndex, int pageSize) {
        PageVo<SupplierCommodity> pageVo = new PageVo<>();

        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(supplierCommodityMapping.querySupplierCommodityByPid(pid));
        pageVo.setTotal(supplierCommodityMapping.querySupplierCommodityByPidCount(pid));
        return pageVo;
    }

    @Override
    public int queryCountByCidOrPid(int cid, int pid) {
        return supplierCommodityMapping.queryCountByCidOrPid(cid,pid);
    }
}
