package com.zilong.controller;

import com.zilong.service.purchaseService.PurchasenoteService;
import com.zilong.service.purchaseService.PurchaseorderService;
import com.zilong.service.purchaseService.SupplierCommodityService;
import com.zilong.vo.PageVo;
import com.zilong.vo.purchase.Purchasenote;
import com.zilong.vo.purchase.Purchaseorder;
import com.zilong.vo.purchase.SupplierCommodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class PurchaseController {
    @Autowired
    PurchaseorderService purchaseorderService;

    @Autowired
    SupplierCommodityService supplierCommodityService;

    // 查询采购订单
    @RequestMapping("/queryPurchaseorder.action")
    public PageVo<Purchaseorder> queryPurchaseorder(@RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "rows", defaultValue = "5") int rows,
                                                    Purchaseorder purchaseorder) {
        return purchaseorderService.queryLike(purchaseorder, page, rows);
    }

    // 查询所有仓库商品信息
    @RequestMapping("/querySupplierCommodity.action")
    public PageVo<SupplierCommodity> querySupplierCommodity(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "rows", defaultValue = "5") int rows,
                                                            SupplierCommodity supplierCommodity,
                                                            @RequestParam(value = "tid", defaultValue = "0") int tid) {
        return supplierCommodityService.queryLike(supplierCommodity, tid, page, rows);
    }

    // 确认采购
    @RequestMapping(value = "/caigouOk.action", produces = "text/html;charset=UTF-8")
    public String caigouOk(String commoditys, String counts, String text, HttpSession session) {
        int row = purchaseorderService.caigouOk(commoditys, counts, text, session);
        return row > 0 ? "采购成功" : "采购失败";
    }


    // 查询销售订单详情表
    @RequestMapping(value = "/queryPurchaseorderDetailsByPid.action")
    public PageVo<SupplierCommodity> queryPurchaseorderDetailsByPid(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "rows", defaultValue = "5") int rows,
            int pid) {
        return supplierCommodityService.querySupplierCommodityByPid(pid, page, rows);
    }

    // 根据商品id和订单id差商品数量
    @RequestMapping(value = "/queryCountByCidOrPid.action")
    public int queryCountByCidOrPid(int cid, int pid) {
        return supplierCommodityService.queryCountByCidOrPid(cid, pid);
    }

    // 查询采购单的备注
    @RequestMapping(value = "/queryPurchaseorderTextByPid.action", produces = "text/html;charset=UTF-8")
    public String queryPurchaseorderTextByPid(int pid) {
        return purchaseorderService.queryPurchaseorderTextByPid(pid);
    }

    //根据id删除订单详情
    @RequestMapping(value = "/delPurchaseorderDetailsByCid.action", produces = "text/html;charset=UTF-8")
    public String delPurchaseorderDetailsByCid(String ids) {
        int row = purchaseorderService.delPurchaseorderDetailsByCid(ids);
        return row > 0 ? "删除成功" : "删除失败";
    }

    //根据id修改订单详情
    @RequestMapping(value = "/uptPurchaseorderDetailsByCid.action", produces = "text/html;charset=UTF-8")
    public String uptPurchaseorderDetailsByCid(String ids, String counts, String text, int pid) {
        int row = purchaseorderService.uptPurchaseorderDetailsByOr(ids, counts, text, pid);
        return row > 0 ? "修改成功" : "修改失败";
    }


    @Autowired
    PurchasenoteService purchasenoteService;

    /**
     * 采购单管理
     */
    @RequestMapping("/queryPurchasenote.action")
    public PageVo<Purchasenote> queryPurchasenote(@RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "rows", defaultValue = "5") int rows,
                                                  Purchasenote purchasenote) {
        return purchasenoteService.queryPurchasenote(purchasenote, page, rows);
    }

    //同意采购订单 生成采购单
    @RequestMapping(value = "/purchaseorderOk.action", produces = "text/html;charset=UTF-8")
    public String purchaseorderOk(int purchaseorferId, HttpSession session) {
        int row = purchasenoteService.addPurchasenote(purchaseorferId, session);
        return row > 0 ? "成功生成采购单" : "生成采购单失败";
    }

    //拒绝采购订单purchaseorderNo
    @RequestMapping(value = "/purchaseorderNo.action", produces = "text/html;charset=UTF-8")
    public String purchaseorderNo(int purchaseorferId) {
        int row = purchaseorderService.turnPurchaserorder(purchaseorferId);
        return row > 0 ? "已拒绝" : "拒绝失败";
    }

    // 入库
    @RequestMapping(value = "/rukuOk.action", produces = "text/html;charset=UTF-8")
    public String ruku(int purchaseorderId,int warehouseId) {
        int row = purchasenoteService.rukuOk(purchaseorderId,warehouseId);
        if(row == 2){
            return "该仓库容量不够";
        }
        return row > 0 ? "入库成功" : "入库失败";
    }
}
