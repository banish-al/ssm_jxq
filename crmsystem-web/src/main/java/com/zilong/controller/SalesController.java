package com.zilong.controller;

import com.zilong.service.salesorderService.SalesorderService;
import com.zilong.service.salesorderService.SalesticketService;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.dataVo.Connection;
import com.zilong.vo.purchase.Purchaseorder;
import com.zilong.vo.salesVo.Salesorder;
import com.zilong.vo.salesVo.Salesticket;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class SalesController {
    @Autowired
    SalesorderService salesorderService;

    @RequestMapping("/querySalesorder.action")
    public PageVo<Salesorder> querySalesorder(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "rows", defaultValue = "5") int rows,
                                              Salesorder salesorder) {
        return salesorderService.querySalesorder(salesorder, page, rows);
    }

    // 生成销售订单表
    @RequestMapping(value = "/addsalesorder.action", produces = "text/html;charset=UTF-8")
    public String addsalesorder(String commoditys, String counts, String text, HttpSession session) {
        int row = salesorderService.addsalesorder(commoditys, counts, text, session);
        return row > 0 ? "提交成功" : "提交失败";
    }

    @RequestMapping("/queryCommodityBySid")
    public List<Commodity> queryCommodityBySid(int sid) {
        return salesorderService.queryCommodityBySid(sid);
    }

    //根据商品id和采购订单id查询商品数量
    @RequestMapping("/queryCountByCidOrSid.action")
    public int queryCountByCidOrSid(int cid, int sid) {
        return salesorderService.queryCountByCidOrSid(cid, sid);
    }

    //根据客户id查销售订单
    @RequestMapping("/querySalesorderByConnectionId.action")
    public PageVo<Salesorder> querySalesorderByConnectionId(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "rows", defaultValue = "5") int rows,
                                                            HttpSession session,Salesorder salesorder) {
        Connection connection = (Connection) session.getAttribute("connection");
        salesorder.setConnection(connection);
        return salesorderService.querySalesorder(salesorder, page, rows);
    }

    /**
     * 销售单
     */
    @Autowired
    SalesticketService salesticketService;

    @RequestMapping("/querySalesticket.action")
    public PageVo<Salesticket> querySalesticket(@RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "rows", defaultValue = "5") int rows,
                                                Salesticket salesticket){
        return salesticketService.querySalesticket(salesticket,page,rows);
    }

    //添加销售单
    @RequestMapping(value = "/addSalesticket.action", produces = "text/html;charset=UTF-8")
    public String addSalesticket(int salesorderId, HttpSession session){
        int row = salesticketService.addSalesticket(salesorderId,session);
        return row > 0 ? "成功生成销售单" : "生成销售单失败";
    }

    //添加销售单
    @RequestMapping(value = "/noSalesticket.action", produces = "text/html;charset=UTF-8")
    public String noSalesticket(int salesorderId){
        int row = salesorderService.noSalesorderStateById(salesorderId);
        return row > 0 ? "已拒绝" : "拒绝失败";
    }

    //查审核人
    @RequestMapping(value = "/queryUserName.action", produces = "text/html;charset=UTF-8")
    public String queryUserName(int sid){
        return salesticketService.queryUserNameBySid(sid);
    }

    // 出库
    @RequestMapping(value = "/chuku.action", produces = "text/html;charset=UTF-8")
    public String chuku(int salesorderId){
        int row = salesticketService.chuku(salesorderId);
        System.out.println(row);
        if(row == 2){
            return "库存不足";
        }
        return row > 0 ? "出库成功" : "出库失败";
    }
}
