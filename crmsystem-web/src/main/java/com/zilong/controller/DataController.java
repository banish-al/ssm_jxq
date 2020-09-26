package com.zilong.controller;

import com.zilong.dao.dataDao.CommodityMapping;
import com.zilong.dao.dataDao.ConnectionMapping;
import com.zilong.service.dataService.CommodityService;
import com.zilong.service.dataService.ConnectionService;
import com.zilong.service.dataService.SupplierService;
import com.zilong.service.dataService.WarehouseService;
import com.zilong.service.systemService.CitysService;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.dataVo.Connection;
import com.zilong.vo.dataVo.Supplier;
import com.zilong.vo.dataVo.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class DataController {

    @Autowired
    SupplierService supplierService;

    @Autowired
    CitysService citysService;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    CommodityService commodityService;

    @RequestMapping("queryAllSupplier.action")
    public PageVo<Supplier> queryAllSupplier(@RequestParam(value = "page", defaultValue = "1") int page,
                                             @RequestParam(value = "rows", defaultValue = "5") int rows,
                                             Supplier supplier) {
        return supplierService.queryAllSupplier(supplier, page, rows);
    }

    //添加供货商  addSupplier.action
    @RequestMapping(value = "addSupplier.action", produces = "text/html;charset=UTF-8")
    public String addSupplier(Supplier supplier,
                              @RequestParam("shen") String shen,
                              @RequestParam("shi") String shi,
                              @RequestParam("qu") String qu) {
        String citys = "";
        if (shen != null) {
            citys += citysService.queryProvinceByCode(shen);
        }
        if (shi != null) {
            citys += "/" + citysService.queryCityByCode(shi);
        }
        if (qu != null) {
            citys += "/" + citysService.queryAreaByCode(qu);
        }
        supplier.setSupplierAddr(citys);
        int row = supplierService.insert(supplier);
        return 1 > 0 ? "添加成功" : "添加失败";
    }

    //修改uptSupplier
    //添加供货商  addSupplier.action
    @RequestMapping(value = "uptSupplier.action", produces = "text/html;charset=UTF-8")
    public String uptSupplier(Supplier supplier,
                                @RequestParam("shen") String shen,
                                @RequestParam("shi") String shi,
                                @RequestParam("qu") String qu) {
        String citys = "";
        try {
            Integer.valueOf(shen);
            if (shen != null) {
                citys += citysService.queryProvinceByCode(shen);
            }
            if (shi != null) {
                citys += "/" + citysService.queryCityByCode(shi);
            }
            if (qu != null) {
                citys += "/" + citysService.queryAreaByCode(qu);
            }
        } catch (Exception q) {
            if (shen != null) {
                citys += shen;
            }
            if (shi != null) {
                citys += "/" + shi;
            }
            if (qu != null) {
                citys += "/" + qu;
            }
        }
        supplier.setSupplierAddr(citys);
        int row = supplierService.update(supplier);
        return row > 0 ? "修改成功" : "修改失败";
    }


    /**
     * 仓库管理
     */
    @RequestMapping("queryLikeWarehouse.action")
    public PageVo<Warehouse> queryLikeWarehouse(@RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "rows", defaultValue = "5") int rows,
                                                Warehouse warehouse) {
        return warehouseService.queryLikeWarehouse(warehouse, page, rows);
    }

    //修改仓库信息uptWarehouse.action
    @RequestMapping(value = "uptWarehouse.action", produces = "text/html;charset=UTF-8")
    public String uptWarehouse(Warehouse warehouse) {
        int row = warehouseService.uptWarehouse(warehouse);
        return row > 0 ? "修改成功" : "修改失败";
    }

    // 根据仓库id查询已用容量
    @RequestMapping("queryWarehouseCountByWid.action")
    public int queryWarehouseCountByWid(int wid) {
        return warehouseService.queryWarehouseCountByWid(wid);
    }

    /**
     * 商品管理
     */
    @RequestMapping("/queryLikeCommodity.action")
    public PageVo<Commodity> queryLikeCommodity(@RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "rows", defaultValue = "5") int rows,
                                                @RequestParam(value = "commodityOndateText", defaultValue = "全部") String commodityOndateText,
                                                Commodity commodity) {
        return commodityService.queryLikeCommodity(commodity, page, rows, commodityOndateText);
    }

    // 修改商品uptCommodity.action
    @RequestMapping(value = "/uptCommodity.action", produces = "text/html;charset=UTF-8")
    public String uptCommodity(Commodity commodity, MultipartFile img, HttpServletRequest request) {
        //把上传的文件  保存到服务端 某个位置
        //获取上传文件的文件名
        try {
            String filename = img.getOriginalFilename();
            //获取项目当前运行的路径     上下文对象
            String path = request.getServletContext().getRealPath("/img");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            //将文件保存到该路径下
            img.transferTo(new File(path, filename));
            commodity.setCommodityImage("img/" + filename);
        } catch (Exception e) {
        }
        int row = commodityService.uptCommodity(commodity);
        return row > 0 ? "修改成功" : "修改失败";
    }

    @RequestMapping(value = "/inCommodity.action", produces = "text/html;charset=UTF-8")
    public String inCommodity(String arr) {
        int row = commodityService.inCommodity(arr);
        return row > 0 ? "下架成功" : "下架失败";
    }

    @RequestMapping(value = "/onCommodity.action", produces = "text/html;charset=UTF-8")
    public String onCommodity(String arr) {
        int row = commodityService.onCommodity(arr);
        return row > 0 ? "上架成功" : "上架失败";
    }

    // 根据商品id查询商品总数
    @RequestMapping("/queryCommodityCountByCid.action")
    public int queryCommodityCountByCid(int cid) {
        return commodityService.queryCommodityCountByCid(cid);
    }

    // 根据商品id查询商品总数
    @RequestMapping("/queryCommodityCountByOneCid.action")
    public int queryCommodityCountByOneCid(int cid, int wid) {
        return commodityService.queryCommodityCountByOneCid(cid, wid);
    }

    // 查询所有仓库信息
    @RequestMapping("/queryAllWarehouse.action")
    public List<Warehouse> queryAllWarehouse() {
        return warehouseService.queryAllWarehouse();
    }

    @Autowired
    ConnectionService connectionService;

    // 查询所有客户信息
    @RequestMapping("/queryConnection.action")
    public PageVo<Connection> queryConnection(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "rows", defaultValue = "5") int rows,
                                              Connection connection) {
        return connectionService.queryLike(connection, page, rows);
    }

    //添加客户
    @RequestMapping(value = "addConnetion.action", produces = "text/html;charset=UTF-8")
    public String addConnetion(Connection connection,
                               @RequestParam("shen") String shen,
                               @RequestParam("shi") String shi,
                               @RequestParam("qu") String qu) {
        String citys = "";
        if (shen != null) {
            citys += citysService.queryProvinceByCode(shen);
        }
        if (shi != null) {
            citys += "/" + citysService.queryCityByCode(shi);
        }
        if (qu != null) {
            citys += "/" + citysService.queryAreaByCode(qu);
        }
        connection.setConnectionAddr(citys);
        connection.setConnectionCount(0);
        connection.setConnectionState(1);
        int row = connectionService.insert(connection);
        return row > 0 ? "添加成功" : "添加失败";
    }
    //修改客户
    @RequestMapping(value = "uptConnection.action", produces = "text/html;charset=UTF-8")
    public String uptConnection(Connection connection,
                               @RequestParam("shen") String shen,
                               @RequestParam("shi") String shi,
                               @RequestParam("qu") String qu) {
        String citys;
        try {
            Integer.valueOf(shen);
            citys = citysService.queryProvinceByCode(shen) + "/" +
                    citysService.queryCityByCode(shi) + "/" +
                    citysService.queryAreaByCode(qu);
        } catch (Exception e) {
            citys = shen + "/" + shi + "/" + qu;
        }
        connection.setConnectionAddr(citys);
        System.out.println(citys);
        System.out.println(connection);
        int row = connectionService.updateById(connection);
        return row > 0 ? "修改成功" : "修改失败";
    }

    @Autowired
    ConnectionMapping connectionMapping;
    //删除酷虎
    @RequestMapping(value = "/delConnectionByCid.action", produces = "text/html;charset=UTF-8")
    public String delConnectionByCid(int cid){
        int row = connectionMapping.delConnectionByCid(cid);
        return row > 0 ? "删除成功" : "删除失败";
    }
}
