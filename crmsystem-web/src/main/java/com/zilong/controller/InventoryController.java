package com.zilong.controller;

import com.zilong.service.queryInventoryService.QueryInventoryService;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InventoryController {

    @Autowired
    QueryInventoryService queryInventoryService;

    @RequestMapping("/queryInventoryByCommodity.action")
    public PageVo<Commodity> queryInventoryByCommodity(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "rows", defaultValue = "5") int rows,
                                                       @RequestParam(value = "wid", defaultValue = "0") int wid,
                                                       Commodity commodity) {
        return queryInventoryService.queryInventoryByCommodity(commodity, wid, page, rows);
    }

    //根据id查询仓库最大容量
    @RequestMapping("/queryWarehouseMaxCount.action")
    public int queryWarehouseMaxCount(int wid) {
        return queryInventoryService.queryWarehouseMaxCount(wid);
    }

    // 仓库转库
    @RequestMapping(value = "/turnInventory.action",produces = "text/html;charset=UTF-8")
    public String turnInventory(int[] turnCommodityId, int[] turnCommodityCount, int turnwid, int wid) {
        int row = queryInventoryService.turnInventory(turnCommodityId,turnCommodityCount,turnwid,wid);
        return row > 0 ? "转库成功" : "转库失败";
    }

}
