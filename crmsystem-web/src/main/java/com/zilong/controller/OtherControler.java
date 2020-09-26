package com.zilong.controller;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.dataDao.CommodityMapping;
import com.zilong.dao.dataDao.CommoditytypeMapping;
import com.zilong.dao.otherDao.OtherMapping;
import com.zilong.service.dataService.ConnectionService;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.dataVo.Commoditytype;
import com.zilong.vo.dataVo.Connection;
import com.zilong.vo.dataVo.Supplier;
import com.zilong.vo.purchase.SupplierCommodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@RestController
public class OtherControler<pub> {

    @Autowired
    OtherMapping otherMapping;

    @Autowired
    CommoditytypeMapping commoditytypeMapping;

    @Autowired
    ConnectionService connectionService;

    @RequestMapping("/other_login.action")
    public int otherLogin(String otherName, String otherPassword, HttpSession session) {
        Supplier supplier = otherMapping.loginSupplier(otherName, otherPassword);
        Connection connection = connectionService.queryByName(otherName);
        if (supplier != null) {
            session.setAttribute("supplier", supplier);
            return 1;
        }
        if (connection != null) {
            session.setAttribute("connection", connection);
            return 2;
        }
        return 0;
    }

    // 根据供货商id查询供货商商品信息
    @RequestMapping("/querySupplierCommodityBySid.action")
    public PageVo<SupplierCommodity> querySupplierCommodityBySid(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                 @RequestParam(value = "rows", defaultValue = "10") int rows,
                                                                 HttpSession session,
                                                                 SupplierCommodity supplierCommodity,
                                                                 @RequestParam(value = "tid", defaultValue = "0") int tid) {
        if(supplierCommodity.getSupplierCommodityName() == ""){
            supplierCommodity.setSupplierCommodityName(null);
        }
        PageVo<SupplierCommodity> pageVo = new PageVo<>();
        Supplier supplier = (Supplier) session.getAttribute("supplier");

        PageHelper.startPage(page, rows);
        pageVo.setRows(otherMapping.querySupplierCommodityBySid(supplierCommodity,supplier.getSupplierId(),tid));

        pageVo.setTotal(otherMapping.querySupplierCommodityBySidCount(supplier.getSupplierId()));

        return pageVo;
    }

    // 获取所有商品类型
    @RequestMapping("/queryAllCommoditytype.action")
    public List<Commoditytype> queryAllCommoditytype() {
        return commoditytypeMapping.queryAll();
    }

    // 修改商品信息
    @RequestMapping(value = "uptSupplierCommodity.action", produces = "text/html;charset=UTF-8")
    public String uptSupplierCommodity(SupplierCommodity supplierCommodity, Integer commoditytypeId, MultipartFile img, HttpServletRequest request) {
        //设置商品属性
        Commoditytype commoditytype = new Commoditytype();
        commoditytype.setCommoditytypeId(commoditytypeId);
        supplierCommodity.setCommoditytype(commoditytype);
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
            supplierCommodity.setSupplierCommodityImage("img/" + filename);
        } catch (Exception e) {
        }
        int row = otherMapping.uptSupplierCommodity(supplierCommodity);
        return row > 0 ? "修改成功" : "修改失败";
    }
    //添加商品
    @RequestMapping(value = "/addSupplierCommodity.action", produces = "text/html;charset=UTF-8")
    public String addSupplierCommodity(SupplierCommodity supplierCommodity, Integer commoditytypeId, MultipartFile img, HttpServletRequest request,HttpSession session){
        //设置商品属性
        Commoditytype commoditytype = new Commoditytype();
        commoditytype.setCommoditytypeId(commoditytypeId);
        supplierCommodity.setCommoditytype(commoditytype);
        Supplier supplier = (Supplier) session.getAttribute("supplier");
        supplierCommodity.setSupplier(supplier);
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
            supplierCommodity.setSupplierCommodityImage("img/" + filename);
        } catch (Exception e) {
        }
        int row = otherMapping.addSupplierCommodity(supplierCommodity);
        return row > 0 ? "添加成功" : "添加失败";
    }


    @Autowired
    CommodityMapping commodityMapping;
    /**
     * 销售商品
     */
    // 查询数量大于0和上架的商品
    @RequestMapping("queryCommoditySales.action")
    public PageVo<Commodity> queryCommoditySales(Commodity commodity,
                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "rows", defaultValue = "8") int rows){
        PageVo<Commodity> pageVo = new PageVo<>();

        PageHelper.startPage(page, rows);
        pageVo.setRows(commodityMapping.queryCommoditySales(commodity));
        pageVo.setTotal(commodityMapping.queryCommoditySalesConut(commodity));
        return pageVo;
    }
}
