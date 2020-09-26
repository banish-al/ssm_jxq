package com.zilong.service.salesorderService.impl;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.dataDao.CommodityMapping;
import com.zilong.dao.dataDao.WarehouseMapping;
import com.zilong.dao.salesDao.SalesorderMapping;
import com.zilong.dao.salesDao.SalesticketMapping;
import com.zilong.service.salesorderService.SalesticketService;
import com.zilong.utils.CreteNumber;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.dataVo.Warehouse;
import com.zilong.vo.salesVo.Salesorder;
import com.zilong.vo.salesVo.Salesticket;
import com.zilong.vo.systemVo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SalesticketServiceImpl implements SalesticketService {

    @Autowired
    SalesticketMapping salesticketMapping;

    @Autowired
    SalesorderMapping salesorderMapping;

    @Override
    public PageVo<Salesticket> querySalesticket(Salesticket salesticket, int startIndex, int pageSize) {
        PageVo<Salesticket> pageVo = new PageVo<>();
        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(salesticketMapping.queryLike(salesticket));

        pageVo.setTotal(salesticketMapping.queryLikeCount(salesticket));
        return pageVo;
    }

    // 同意销售订单，生成销售单
    @Override
    public int addSalesticket(int salesorderId, HttpSession session) {
        CreteNumber creteNumber = new CreteNumber();
        // 拿到销售人
        Users users = (Users) session.getAttribute("user");
        // 根据id查到销售订单
        Salesorder salesorder = salesorderMapping.querySalesorderById(salesorderId);
        //创建销售单对象，添加
        Salesticket salesticket = new Salesticket(null, salesorder, users,
                creteNumber.dateFormat(creteNumber.creteDate(0)), "揽货中", salesorder.getSalesorderText());
        int i = salesticketMapping.insert(salesticket);
        if (i > 0) {
            i *= salesticketMapping.addConnectionCountBySid(salesorderId);
            i *= salesorderMapping.uptSalesorderStateById(salesorderId);
        }
        return i;
    }

    @Override
    public String queryUserNameBySid(int sid) {
        return salesticketMapping.queryUserBySalesorder(sid);
    }

    @Autowired
    CommodityMapping commodityMapping;
    @Autowired
    WarehouseMapping warehouseMapping;

    @Override
    public int chuku(int salesorderId) {
        List<Commodity> commodity = commodityMapping.queryCommodityBySid(salesorderId);

        int f = 1;
        //判断有没有商品数量不足
        for (Commodity c : commodity) {
            int cid = c.getCommodityId();
            int count = salesticketMapping.queryCountByCidOrSid(cid, salesorderId);
            if (commodityMapping.queryCommodityCountByCid(cid) < count) {
                f = 2;
            }
        }
        if (f == 2) {
            return 2;
        }


        try {
            //循环商品，出库
            for (Commodity c : commodity) {
                //获取商品id和商品数量
                int cid = c.getCommodityId();
                int count = salesticketMapping.queryCountByCidOrSid(cid, salesorderId);
                //System.out.println("cid:"+cid+"count:"+count);
                //循环仓库
                for (Warehouse w : warehouseMapping.queryAll()) {
                    //判断此仓库是否有此商品
                    //如果有此商品
                    try {
                        //System.out.println("cid仓库数量："+warehouseMapping.queryCountByCidOrWid(cid, w.getWarehouseId()));
                    }catch (Exception e){
                        //System.out.println("此仓库没有cid");
                    }
                    if (warehouseMapping.existsByCidOrWarehouseId(cid, w.getWarehouseId()) > 0) {
                        //System.out.println("有");
                        //如果商品数量大于库存数量
                        if (count > warehouseMapping.queryCountByCidOrWid(cid, w.getWarehouseId())) {
                            //System.out.println(">");
                            //商品数量减库存数量
                            count -= warehouseMapping.queryCountByCidOrWid(cid, w.getWarehouseId());
                            //System.out.println("-后count"+count);
                            //就将此库存的商品删除
                            warehouseMapping.delWarehouseCommodityByCidOrWid(cid, w.getWarehouseId());
                            //System.out.println("del");
                        } else {
                            //如果商品数量<=库存数量
                            //System.out.println("刚好");
                            warehouseMapping.lessenCidByTurnWarehouseId(cid, count, w.getWarehouseId());
                            break;
                        }
                    } else {
                        //如果没有此商品
                        //System.out.println(w.getWarehouseId()+"出库没有"+c.getCommodityName());
                    }
                }
                //删除数量为0的仓库
                warehouseMapping.delCidByCountIsZero();
                f *= salesticketMapping.uptSalesticketChukuOk(salesorderId);
                //改变该商品的销售日期
                f *= commodityMapping.uptCommoidtyOnDate(cid);
            }
        } catch (Exception e) {
            //System.out.println("出库异常");
            f = 0;
        }

        return f;
    }
}
