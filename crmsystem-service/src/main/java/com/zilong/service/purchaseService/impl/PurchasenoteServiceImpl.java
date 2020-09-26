package com.zilong.service.purchaseService.impl;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.dataDao.CommodityMapping;
import com.zilong.dao.dataDao.WarehouseMapping;
import com.zilong.dao.purchaseDao.PurchasenoteMapping;
import com.zilong.dao.purchaseDao.PurchaseorderMapping;
import com.zilong.dao.purchaseDao.SupplierCommodityMapping;
import com.zilong.service.purchaseService.PurchasenoteService;
import com.zilong.utils.CreteNumber;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.dataVo.Warehouse;
import com.zilong.vo.purchase.Purchasenote;
import com.zilong.vo.purchase.Purchaseorder;
import com.zilong.vo.purchase.SupplierCommodity;
import com.zilong.vo.systemVo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class PurchasenoteServiceImpl implements PurchasenoteService {

    @Autowired
    PurchasenoteMapping purchasenoteMapping;

    @Autowired
    PurchaseorderMapping purchaseorderMapping;

    @Autowired
    SupplierCommodityMapping supplierCommodityMapping;

    @Autowired
    CommodityMapping commodityMapping;

    @Autowired
    WarehouseMapping warehouseMapping;

    @Override
    public PageVo<Purchasenote> queryPurchasenote(Purchasenote purchasenote, int startIndex, int pageSize) {
        PageVo<Purchasenote> pageVo = new PageVo<>();

        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(purchasenoteMapping.queryLike(purchasenote));
        pageVo.setTotal(purchasenoteMapping.queryLikeCount(purchasenote));
        return pageVo;
    }

    @Override
    public int addPurchasenote(int purchaseorderId, HttpSession session) {
        // 获取需要数据
        CreteNumber creteNumber = new CreteNumber();
        Users users = (Users) session.getAttribute("user");
        String text = purchaseorderMapping.queryPurchaseorderTextByPid(purchaseorderId);
        Purchaseorder purchaseorder = new Purchaseorder();
        purchaseorder.setPurchaseorderId(purchaseorderId);
        purchaseorder.setPurchaseorderState("已同意");
        //设置采购单数据
        Purchasenote purchasenote = new Purchasenote();
        purchasenote.setPurchaseorder(purchaseorder);
        purchasenote.setUsers(users);
        purchasenote.setPurchasenoteDate(creteNumber.dateFormat(creteNumber.creteDate(0)));
        purchasenote.setPurchasenoteState("采购中");
        purchasenote.setPurchasenoteText(text);

        int row = 1;
        //改变采购订单的状态
        row *= purchaseorderMapping.updateById(purchaseorder);
        //新增采购单
        row *= purchasenoteMapping.addPurchasenote(purchasenote);
        return row;
    }

    @Override
    public int rukuOk(int purchaseorderId,int warehouseId) {
        if(purchaseorderMapping.queryCountByPid(purchaseorderId) > warehouseMapping.queryWarehouseMaxCount(warehouseId)){
            return 2;
        }
        CreteNumber creteNumber = new CreteNumber();
        // 根据订单id拿到所有商品信息
        List<SupplierCommodity> supplierCommoditys = supplierCommodityMapping.querySupplierCommodityByPid(purchaseorderId);
        int i = 1;
        for (SupplierCommodity supplierCommodity: supplierCommoditys) {
            // 根据订单id和商品id查进货的商品数量
            int count = supplierCommodityMapping.queryCountByCidOrPid(supplierCommodity.getSupplierCommodityId(),purchaseorderId);

            //判断商品是否存在
            Commodity cc = commodityMapping.queryCommodityByCnameOrSid(supplierCommodity.getSupplierCommodityName(),supplierCommodity.getSupplier().getSupplierId());
            if(cc != null){
                //如果仓库已有此商品
                if(warehouseMapping.existsByCidOrWarehouseId(cc.getCommodityId(),warehouseId) >0){
                    //设置添加商品数量
                    i *= warehouseMapping.setCidByWarehouseId(cc.getCommodityId(),count,warehouseId);
                }else {
                    //如果此仓库没有此商品
                    //添加商品和数量
                    i *= warehouseMapping.addCidByWarehouseId(cc.getCommodityId(),count,warehouseId);
                }
                //修改商品的进货日期
                i *= commodityMapping.setCommodityInDate(cc.getCommodityId(),cc.getSupplier().getSupplierId());
            }else {
                //如果商品不存在
                //添加商品
                Commodity commodity = new Commodity();
                commodity.setCommodityName(supplierCommodity.getSupplierCommodityName());
                commodity.setCommodityCoding(supplierCommodity.getCommoditytype().getCommoditytypeCoding()+
                        creteNumber.creteDateNumber()+creteNumber.creteRandomNumber(4));
                commodity.setCommodityImage(supplierCommodity.getSupplierCommodityImage());
                commodity.setCommodityInprice(supplierCommodity.getSupplierCommodityOnprice()+
                        (supplierCommodity.getSupplierCommodityOnprice()*0.3f));
                commodity.setCommodityOnprice(supplierCommodity.getSupplierCommodityOnprice());
                commodity.setCommodityIndate(creteNumber.dateFormat(creteNumber.creteDate(0)));
                commodity.setCommodityState("上架");
                commodity.setCommoditytype(supplierCommodity.getCommoditytype());
                commodity.setSupplier(supplierCommodity.getSupplier());
                System.out.println("不存在"+commodity);
                i *= commodityMapping.insert(commodity,warehouseId);
                //添加商品数量
                i *= warehouseMapping.addCidByWarehouseId(commodity.getCommodityId(),count,warehouseId);
                //修改商品的进货日期
                i *= commodityMapping.setCommodityInDate(commodity.getCommodityId(),commodity.getSupplier().getSupplierId());
            }

        }
        //设置成已入库
        purchasenoteMapping.setyirukuBypid(purchaseorderId);
        return i;
    }


}
