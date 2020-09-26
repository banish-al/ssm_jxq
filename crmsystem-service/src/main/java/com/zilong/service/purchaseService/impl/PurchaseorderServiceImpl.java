package com.zilong.service.purchaseService.impl;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.purchaseDao.PurchaseorderMapping;
import com.zilong.dao.purchaseDao.SupplierCommodityMapping;
import com.zilong.service.purchaseService.PurchaseorderService;
import com.zilong.utils.CreteNumber;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.purchase.Purchaseorder;
import com.zilong.vo.systemVo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseorderServiceImpl implements PurchaseorderService {
    @Autowired
    PurchaseorderMapping purchaseorderMapping;
    @Autowired
    SupplierCommodityMapping supplierCommodityMapping;

    @Override
    public PageVo<Purchaseorder> queryLike(Purchaseorder purchaseorder, int startIndex, int pageSize) {
        PageVo<Purchaseorder> pageVo = new PageVo<>();

        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(purchaseorderMapping.queryLike(purchaseorder));

        pageVo.setTotal(purchaseorderMapping.queryLikeCount(purchaseorder));
        return pageVo;
    }

    @Override
    public int caigouOk(String commoditys, String counts, String text, HttpSession session) {
        // 结果 f
        int f = 1;

        // commodity 商品id     count 商品数量   text 备注
        String[] commodity = commoditys.split(",");
        String[] count = counts.split(",");
        // 创建订单
        //获取用户id
        Users users = (Users) session.getAttribute("user");
        //新建订单编号
        CreteNumber creteNumber = new CreteNumber();
        String purchaseorderNumber = creteNumber.creteDateNumber() + creteNumber.creteRandomNumber(4);
        // 新建date当前时间
        Date purchaseorderCreteDate = creteNumber.dateFormat(creteNumber.creteDate(0));
        // 新建date订单有效日期
        Date purchaseorderValidDate = creteNumber.dateFormat(creteNumber.creteDate(7));
        // 得到订单商品的总价
        float purchaseorderPrice = 0;
        for (int c = 0; c < commodity.length; c++) {
            purchaseorderPrice += supplierCommodityMapping.queryPriceByCid(Integer.valueOf(commodity[c])) * Integer.valueOf(count[c]);
        }
        // 设置订单对象
        Purchaseorder purchaseorder = new Purchaseorder();
        purchaseorder.setPurchaseorderNumber(purchaseorderNumber);
        purchaseorder.setPurchaseorderCreatedate(purchaseorderCreteDate);
        purchaseorder.setPurchaseorderValiddate(purchaseorderValidDate);
        purchaseorder.setUsers(users);
        purchaseorder.setPurchaseorderTotalprice(purchaseorderPrice);
        purchaseorder.setPurchaseorderState("待审核");
        purchaseorder.setPurchaseorderText(text);
        // 执行添加方法
        f *= purchaseorderMapping.insert(purchaseorder);


        // 根据订单id创建订单详情
        // 获取刚刚添加代购订单id
        int pid = purchaseorder.getPurchaseorderId();
        for (int i = 0; i < commodity.length; i++) {
            f *= purchaseorderMapping.addPurchaseorderDetails(pid, Integer.valueOf(commodity[i]), Integer.valueOf(count[i]));
        }

        return f;
    }

    @Override
    public String queryPurchaseorderTextByPid(int pid) {
        return purchaseorderMapping.queryPurchaseorderTextByPid(pid);
    }

    @Override
    public int delPurchaseorderDetailsByCid(String ids) {
        int i = 1;
        String[] strings = ids.split(",");
        for (String cid : strings) {
            i *= purchaseorderMapping.delPurchaseorderDetailsByCid(Integer.valueOf(cid));
        }
        return i;
    }

    @Override
    public int uptPurchaseorderDetailsByOr(String ids, String counts, String text, int pid) {
        String[] cid = ids.split(",");
        String[] countId = counts.split(",");
        int j = 1;
        float price = 0;
        for (int i = 0; i < cid.length; i++) {
            j *= purchaseorderMapping.uptCountByCidOrPid(Integer.valueOf(cid[i]), pid, Integer.valueOf(countId[i]));
            price += supplierCommodityMapping.queryPriceByCid(Integer.valueOf(cid[i])) * Integer.valueOf(countId[i]);
        }
        j *= purchaseorderMapping.uptPriceByPid(pid,price);
        j *= purchaseorderMapping.uptTextByPid(pid, text);
        return j;
    }

    @Override
    public int purchaseorderOk(int purchaseorferId) {


        return 0;
    }

    @Override
    public int turnPurchaserorder(int pid) {
        return purchaseorderMapping.turnPurchaserorder(pid);
    }
}
