package com.zilong.vo.purchase;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zilong.vo.dataVo.Commoditytype;
import com.zilong.vo.dataVo.Supplier;

import java.io.Serializable;

/**
 * 表名 :  supplier_commodity<br/>
 * 表注释 : 供货商商品表
 */
@JsonIgnoreProperties(value = "handler")
public class SupplierCommodity implements Serializable {

    /**
     * 供货商商品id
     */
    private Integer supplierCommodityId;
    /**
     * 供货商商品名字
     */
    private String supplierCommodityName;
    /**
     * 供货商商品进货价
     */
    private Float supplierCommodityOnprice;
    /**
     * 供货商商品图片
     */
    private String supplierCommodityImage;
    /**
     * 供货商商品类别id
     */
    private Commoditytype commoditytype;
    /**
     * 供货商商品商家id
     */
    private Supplier supplier;

    public SupplierCommodity() {
        super();
    }

	public SupplierCommodity(Integer supplierCommodityId, String supplierCommodityName, Float supplierCommodityOnprice, String supplierCommodityImage, Commoditytype commoditytype, Supplier supplier) {
		this.supplierCommodityId = supplierCommodityId;
		this.supplierCommodityName = supplierCommodityName;
		this.supplierCommodityOnprice = supplierCommodityOnprice;
		this.supplierCommodityImage = supplierCommodityImage;
		this.commoditytype = commoditytype;
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "SupplierCommodity{" +
				"supplierCommodityId=" + supplierCommodityId +
				", supplierCommodityName='" + supplierCommodityName + '\'' +
				", supplierCommodityOnprice=" + supplierCommodityOnprice +
				", supplierCommodityImage='" + supplierCommodityImage + '\'' +
				", commoditytype=" + commoditytype +
				", supplier=" + supplier +
				'}';
	}

	public Integer getSupplierCommodityId() {
		return supplierCommodityId;
	}

	public void setSupplierCommodityId(Integer supplierCommodityId) {
		this.supplierCommodityId = supplierCommodityId;
	}

	public String getSupplierCommodityName() {
		return supplierCommodityName;
	}

	public void setSupplierCommodityName(String supplierCommodityName) {
		this.supplierCommodityName = supplierCommodityName;
	}

	public Float getSupplierCommodityOnprice() {
		return supplierCommodityOnprice;
	}

	public void setSupplierCommodityOnprice(Float supplierCommodityOnprice) {
		this.supplierCommodityOnprice = supplierCommodityOnprice;
	}

	public String getSupplierCommodityImage() {
		return supplierCommodityImage;
	}

	public void setSupplierCommodityImage(String supplierCommodityImage) {
		this.supplierCommodityImage = supplierCommodityImage;
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
}

