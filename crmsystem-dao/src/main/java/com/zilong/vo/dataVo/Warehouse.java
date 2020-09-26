package com.zilong.vo.dataVo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import java.util.List;

/**
 * 表名 :  warehouse<br/>
 * 表注释 : 仓库表
 */
@JsonIgnoreProperties(value = "handler")
public class Warehouse implements Serializable {

    /**
     * 仓库id
     */
    private Integer warehouseId;
    /**
     * 仓库名
     */
    private String warehouseName;
    /**
     * 最大储存
     */
    private Integer warehouseMaxinventory;
    /**
     * 商品集合
     */
    private List<Commodity> commoditys;

    public Warehouse() {
        super();
    }

    public Warehouse(Integer warehouseId, String warehouseName, Integer warehouseMaxinventory, List<Commodity> commoditys) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseMaxinventory = warehouseMaxinventory;
        this.commoditys = commoditys;
    }

    /**
     * 设置"仓库id"
     */
    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * 获取"仓库id"
     */
    public Integer getWarehouseId() {
        return warehouseId;
    }

    /**
     * 设置"仓库名"
     */
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    /**
     * 获取"仓库名"
     */
    public String getWarehouseName() {
        return warehouseName;
    }

    /**
     * 设置"最大储存"
     */
    public void setWarehouseMaxinventory(Integer warehouseMaxinventory) {
        this.warehouseMaxinventory = warehouseMaxinventory;
    }

    /**
     * 获取"最大储存"
     */
    public Integer getWarehouseMaxinventory() {
        return warehouseMaxinventory;
    }

    public void setCommoditys(List<Commodity> commoditys) {
        this.commoditys = commoditys;
    }

    public List<Commodity> getCommoditys() {
        return commoditys;
    }

    @Override
    public String toString() {
        return "warehouse[" +
                "warehouseId=" + warehouseId +
                ", warehouseName=" + warehouseName +
                ", warehouseMaxinventory=" + warehouseMaxinventory +
                ", commoditys=" + commoditys +
                "			]";
    }
}

