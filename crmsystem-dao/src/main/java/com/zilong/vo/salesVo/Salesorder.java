package com.zilong.vo.salesVo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zilong.vo.dataVo.Connection;

import java.io.Serializable;

/**
 * 表名 :  salesorder<br/>
 * 表注释 : 销售订单表
 */
@JsonIgnoreProperties(value = "handler")
public class Salesorder implements Serializable {

    /**
     * 销售订单id
     */
    private Integer salesorderId;
    /**
     * 销售订单编号
     */
    private String salesorderNumber;
    /**
     * 订单生成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date salesorderCreatedate;
    /**
     * 订单有效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date salesorderValiddate;
    /**
     * 总价格
     */
    private Float salesorderTotalprice;
    /**
     * 客户id
     */
    private Connection connection;
    /**
     * 订单状态
     */
    private String salesorderState;
    /**
     * 备注
     */
    private String salesorderText;

    public Salesorder() {
        super();
    }

	public Salesorder(Integer salesorderId, String salesorderNumber, Date salesorderCreatedate, Date salesorderValiddate, Float salesorderTotalprice, Connection connection, String salesorderState, String salesorderText) {
		this.salesorderId = salesorderId;
		this.salesorderNumber = salesorderNumber;
		this.salesorderCreatedate = salesorderCreatedate;
		this.salesorderValiddate = salesorderValiddate;
		this.salesorderTotalprice = salesorderTotalprice;
		this.connection = connection;
		this.salesorderState = salesorderState;
		this.salesorderText = salesorderText;
	}

	public Integer getSalesorderId() {
		return salesorderId;
	}

	public void setSalesorderId(Integer salesorderId) {
		this.salesorderId = salesorderId;
	}

	public String getSalesorderNumber() {
		return salesorderNumber;
	}

	public void setSalesorderNumber(String salesorderNumber) {
		this.salesorderNumber = salesorderNumber;
	}

	public Date getSalesorderCreatedate() {
		return salesorderCreatedate;
	}

	public void setSalesorderCreatedate(Date salesorderCreatedate) {
		this.salesorderCreatedate = salesorderCreatedate;
	}

	public Date getSalesorderValiddate() {
		return salesorderValiddate;
	}

	public void setSalesorderValiddate(Date salesorderValiddate) {
		this.salesorderValiddate = salesorderValiddate;
	}

	public Float getSalesorderTotalprice() {
		return salesorderTotalprice;
	}

	public void setSalesorderTotalprice(Float salesorderTotalprice) {
		this.salesorderTotalprice = salesorderTotalprice;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getSalesorderState() {
		return salesorderState;
	}

	public void setSalesorderState(String salesorderState) {
		this.salesorderState = salesorderState;
	}

	public String getSalesorderText() {
		return salesorderText;
	}

	public void setSalesorderText(String salesorderText) {
		this.salesorderText = salesorderText;
	}

	@Override
	public String toString() {
		return "Salesorder{" +
				"salesorderId=" + salesorderId +
				", salesorderNumber='" + salesorderNumber + '\'' +
				", salesorderCreatedate=" + salesorderCreatedate +
				", salesorderValiddate=" + salesorderValiddate +
				", salesorderTotalprice=" + salesorderTotalprice +
				", connection=" + connection +
				", salesorderState='" + salesorderState + '\'' +
				", salesorderText='" + salesorderText + '\'' +
				'}';
	}
}

