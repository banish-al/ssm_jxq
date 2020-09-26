package com.zilong.vo.dataVo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * 表名 :  commodity<br/>
 * 表注释 : 商品表
 */
@JsonIgnoreProperties(value = "handler")
public class Commodity implements Serializable {

    /**
     * 商品id
     */
    private Integer commodityId;
    /**
     * 商品名
     */
    private String commodityName;
    /**
     * 商品编号
     */
    private String commodityCoding;
    /**
     * 商品图片
     */
    private String commodityImage;
    /**
     * 进货价
     */
    private Float commodityInprice;
    /**
     * 销售价
     */
    private Float commodityOnprice;
    /**
     * 进货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date commodityIndate;
    /**
     * 最近销售日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date commodityOndate;

    /**
     * 商品状态
     */
    private String commodityState;

    /**
     * 商品类别id
     */
    private Commoditytype commoditytype;
    /**
     * 供应商id
     */
    private Supplier supplier;
    /**
     * 仓库id
     */
    private List<Warehouse> warehouses;

    public Commodity() {
        super();
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityCoding() {
        return commodityCoding;
    }

    public void setCommodityCoding(String commodityCoding) {
        this.commodityCoding = commodityCoding;
    }

    public String getCommodityImage() {
        return commodityImage;
    }

    public void setCommodityImage(String commodityImage) {
        this.commodityImage = commodityImage;
    }

    public Float getCommodityInprice() {
        return commodityInprice;
    }

    public void setCommodityInprice(Float commodityInprice) {
        this.commodityInprice = commodityInprice;
    }

    public Float getCommodityOnprice() {
        return commodityOnprice;
    }

    public void setCommodityOnprice(Float commodityOnprice) {
        this.commodityOnprice = commodityOnprice;
    }

    public Date getCommodityIndate() {
        return commodityIndate;
    }

    public void setCommodityIndate(Date commodityIndate) {
        this.commodityIndate = commodityIndate;
    }

    public Date getCommodityOndate() {
        return commodityOndate;
    }

    public void setCommodityOndate(Date commodityOndate) {
        this.commodityOndate = commodityOndate;
    }

    public String getCommodityState() {
        return commodityState;
    }

    public void setCommodityState(String commodityState) {
        this.commodityState = commodityState;
    }

    public Commoditytype getCommoditytype() {
        return commoditytype;
    }

    public void setCommoditytype(Commoditytype commoditytype) {
        this.commoditytype = commoditytype;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "commodityId=" + commodityId +
                ", commodityName='" + commodityName + '\'' +
                ", commodityCoding='" + commodityCoding + '\'' +
                ", commodityImage='" + commodityImage + '\'' +
                ", commodityInprice=" + commodityInprice +
                ", commodityOnprice=" + commodityOnprice +
                ", commodityIndate=" + commodityIndate +
                ", commodityOndate=" + commodityOndate +
                ", commodityState='" + commodityState + '\'' +
                ", commoditytype=" + commoditytype +
                ", supplier=" + supplier +
                ", warehouses=" + warehouses +
                '}';
    }

    public Commodity(Integer commodityId, String commodityName, String commodityCoding, String commodityImage, Float commodityInprice, Float commodityOnprice, Date commodityIndate, Date commodityOndate, String commodityState, Commoditytype commoditytype, Supplier supplier, List<Warehouse> warehouses) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.commodityCoding = commodityCoding;
        this.commodityImage = commodityImage;
        this.commodityInprice = commodityInprice;
        this.commodityOnprice = commodityOnprice;
        this.commodityIndate = commodityIndate;
        this.commodityOndate = commodityOndate;
        this.commodityState = commodityState;
        this.commoditytype = commoditytype;
        this.supplier = supplier;
        this.warehouses = warehouses;
    }
}

