package com.zilong.vo.purchase;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zilong.vo.systemVo.Users;

import java.io.Serializable;

/**
 * 表名 :  purchaseorder<br/>
 * 表注释 : 采购订单表
 */
@JsonIgnoreProperties(value = "handler")
public class Purchaseorder implements Serializable {

    /**
     * 采购订单id
     */
    private Integer purchaseorderId;
    /**
     * 采购订单编号
     */
    private String purchaseorderNumber;
    /**
     * 采购订单生成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date purchaseorderCreatedate;
    /**
     * 采购订单有效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date purchaseorderValiddate;

    // 采购人
    private Users users;

    // 总价
    private Float purchaseorderTotalprice;

    /**
     * 采购订单状态
     */
    private String purchaseorderState;
    /**
     * 采购订单备注
     */
    private String purchaseorderText;

    public Purchaseorder() {
        super();
    }

    public Purchaseorder(Integer purchaseorderId, String purchaseorderNumber, Date purchaseorderCreatedate, Date purchaseorderValiddate, Users users, Float purchaseorderTotalprice, String purchaseorderState, String purchaseorderText) {
        this.purchaseorderId = purchaseorderId;
        this.purchaseorderNumber = purchaseorderNumber;
        this.purchaseorderCreatedate = purchaseorderCreatedate;
        this.purchaseorderValiddate = purchaseorderValiddate;
        this.users = users;
        this.purchaseorderTotalprice = purchaseorderTotalprice;
        this.purchaseorderState = purchaseorderState;
        this.purchaseorderText = purchaseorderText;
    }

    public Integer getPurchaseorderId() {
        return purchaseorderId;
    }

    public void setPurchaseorderId(Integer purchaseorderId) {
        this.purchaseorderId = purchaseorderId;
    }

    public String getPurchaseorderNumber() {
        return purchaseorderNumber;
    }

    public void setPurchaseorderNumber(String purchaseorderNumber) {
        this.purchaseorderNumber = purchaseorderNumber;
    }

    public Date getPurchaseorderCreatedate() {
        return purchaseorderCreatedate;
    }

    public void setPurchaseorderCreatedate(Date purchaseorderCreatedate) {
        this.purchaseorderCreatedate = purchaseorderCreatedate;
    }

    public Date getPurchaseorderValiddate() {
        return purchaseorderValiddate;
    }

    public void setPurchaseorderValiddate(Date purchaseorderValiddate) {
        this.purchaseorderValiddate = purchaseorderValiddate;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Float getPurchaseorderTotalprice() {
        return purchaseorderTotalprice;
    }

    public void setPurchaseorderTotalprice(Float purchaseorderTotalprice) {
        this.purchaseorderTotalprice = purchaseorderTotalprice;
    }

    public String getPurchaseorderState() {
        return purchaseorderState;
    }

    public void setPurchaseorderState(String purchaseorderState) {
        this.purchaseorderState = purchaseorderState;
    }

    public String getPurchaseorderText() {
        return purchaseorderText;
    }

    public void setPurchaseorderText(String purchaseorderText) {
        this.purchaseorderText = purchaseorderText;
    }

    @Override
    public String toString() {
        return "Purchaseorder{" +
                "purchaseorderId=" + purchaseorderId +
                ", purchaseorderNumber='" + purchaseorderNumber + '\'' +
                ", purchaseorderCreatedate=" + purchaseorderCreatedate +
                ", purchaseorderValiddate=" + purchaseorderValiddate +
                ", users=" + users +
                ", purchaseorderTotalprice=" + purchaseorderTotalprice +
                ", purchaseorderState='" + purchaseorderState + '\'' +
                ", purchaseorderText='" + purchaseorderText + '\'' +
                '}';
    }
}

