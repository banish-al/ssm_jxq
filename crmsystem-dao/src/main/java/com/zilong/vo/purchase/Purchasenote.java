package com.zilong.vo.purchase;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zilong.vo.systemVo.Users;

import java.io.Serializable;

/**
 * 表名 :  purchasenote<br/>
 * 表注释 : 采购单表
 */
@JsonIgnoreProperties(value = "handler")
public class Purchasenote implements Serializable {

    /**
     * 采购单id
     */
    private Integer purchasenoteId;
    /**
     * 采购订单id
     */
    private Purchaseorder purchaseorder;
    /**
     * 采购人员id
     */
    private Users users;
    /**
     * 采购时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date purchasenoteDate;
    /**
     * 采购状态
     */
    private String purchasenoteState;
    /**
     * 备注
     */
    private String purchasenoteText;

    public Purchasenote() {
        super();
    }

    public Purchasenote(Integer purchasenoteId, Purchaseorder purchaseorder, Users users, Date purchasenoteDate, String purchasenoteState, String purchasenoteText) {
        this.purchasenoteId = purchasenoteId;
        this.purchaseorder = purchaseorder;
        this.users = users;
        this.purchasenoteDate = purchasenoteDate;
        this.purchasenoteState = purchasenoteState;
        this.purchasenoteText = purchasenoteText;
    }

    public Integer getPurchasenoteId() {
        return purchasenoteId;
    }

    public void setPurchasenoteId(Integer purchasenoteId) {
        this.purchasenoteId = purchasenoteId;
    }

    public Purchaseorder getPurchaseorder() {
        return purchaseorder;
    }

    public void setPurchaseorder(Purchaseorder purchaseorder) {
        this.purchaseorder = purchaseorder;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Date getPurchasenoteDate() {
        return purchasenoteDate;
    }

    public void setPurchasenoteDate(Date purchasenoteDate) {
        this.purchasenoteDate = purchasenoteDate;
    }

    public String getPurchasenoteState() {
        return purchasenoteState;
    }

    public void setPurchasenoteState(String purchasenoteState) {
        this.purchasenoteState = purchasenoteState;
    }

    public String getPurchasenoteText() {
        return purchasenoteText;
    }

    public void setPurchasenoteText(String purchasenoteText) {
        this.purchasenoteText = purchasenoteText;
    }

    @Override
    public String toString() {
        return "Purchasenote{" +
                "purchasenoteId=" + purchasenoteId +
                ", purchaseorder=" + purchaseorder +
                ", users=" + users +
                ", purchasenoteDate=" + purchasenoteDate +
                ", purchasenoteState='" + purchasenoteState + '\'' +
                ", purchasenoteText='" + purchasenoteText + '\'' +
                '}';
    }
}

