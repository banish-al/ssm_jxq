package com.zilong.service.salesorderService.impl;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.dataDao.CommodityMapping;
import com.zilong.dao.salesDao.SalesorderMapping;
import com.zilong.service.salesorderService.SalesorderService;
import com.zilong.utils.CreteNumber;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.dataVo.Connection;
import com.zilong.vo.salesVo.Salesorder;
import com.zilong.vo.systemVo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class salesorderServiceImpl implements SalesorderService {

    @Autowired
    SalesorderMapping salesorderMapping;

    @Autowired
    CommodityMapping commodityMapping;

    @Override
    public PageVo<Salesorder> querySalesorder(Salesorder salesorder, int startIndex, int pageSize) {
        PageVo<Salesorder> pageVo = new PageVo<>();
        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(salesorderMapping.queryLike(salesorder));

        pageVo.setTotal(salesorderMapping.queryLikeCount(salesorder));
        return pageVo;
    }

    @Override
    public int addsalesorder(String commoditys, String counts, String text, HttpSession session) {
        int o = 1;
        CreteNumber creteNumber = new CreteNumber();
        Connection connection = (Connection) session.getAttribute("connection");
        String[] commodity = commoditys.split(",");
        String[] count = counts.split(",");
        //先生成销售订单
        Salesorder salesorder = new Salesorder();
        salesorder.setSalesorderNumber(creteNumber.creteDateNumber() + creteNumber.creteRandomNumber(4));
        salesorder.setSalesorderCreatedate(creteNumber.dateFormat(creteNumber.creteDate(0)));
        salesorder.setSalesorderValiddate(creteNumber.dateFormat(creteNumber.creteDate(7)));
        float m = 0;
        for (int i = 0; i < commodity.length; i++) {
            m += commodityMapping.queryMoneyByCid(Integer.valueOf(commodity[i])) * Integer.valueOf(count[i]);
        }
        salesorder.setSalesorderTotalprice(m);
        salesorder.setConnection(connection);
        salesorder.setSalesorderState("待审核");
        salesorder.setSalesorderText(text);
        salesorderMapping.addSalesorder(salesorder);
        int id = salesorder.getSalesorderId();
        //再生成销售详情单
        for (int i = 0; i < commodity.length; i++) {
            o *= salesorderMapping.addSalesorderDetails(id, Integer.valueOf(commodity[i]), Integer.valueOf(count[i]));
        }
        return o;
    }

    @Override
    public List<Commodity> queryCommodityBySid(int sid) {
        return commodityMapping.queryCommodityBySid(sid);
    }

    @Override
    public int queryCountByCidOrSid(int cid, int sid) {
        return salesorderMapping.queryCountByCidOrSid2(cid,sid);
    }

    @Override
    public int noSalesorderStateById(int salesorderId) {
        return salesorderMapping.noSalesorderStateById(salesorderId);
    }
}
