package com.zilong.vo.dataVo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import java.util.List;

/**
 * 表名 :  supplier<br/>
 * 表注释 : 供货商表
 */
@JsonIgnoreProperties(value = "handler")
public class Supplier implements Serializable {

    /**
     * 供货商id
     */
    private Integer supplierId;
    /**
     * 供货商名
     */
    private String supplierName;
    /**
     * 供货商地址
     */
    private String supplierAddr;
    /**
     * 供货商邮箱
     */
    private String supplierMailbox;

    //供货商登录密码
    private String supplierPassword;

    public Supplier(Integer supplierId, String supplierName, String supplierAddr, String supplierMailbox, String supplierPassword) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierAddr = supplierAddr;
        this.supplierMailbox = supplierMailbox;
        this.supplierPassword = supplierPassword;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", supplierAddr='" + supplierAddr + '\'' +
                ", supplierMailbox='" + supplierMailbox + '\'' +
                ", supplierPassword='" + supplierPassword + '\'' +
                '}';
    }

    public String getSupplierPassword() {
        return supplierPassword;
    }

    public void setSupplierPassword(String supplierPassword) {
        this.supplierPassword = supplierPassword;
    }

    public Supplier() {
        super();
    }


    /**
     * 设置"供货商id"
     */
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取"供货商id"
     */
    public Integer getSupplierId() {
        return supplierId;
    }

    /**
     * 设置"供货商名"
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 获取"供货商名"
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 设置"供货商地址"
     */
    public void setSupplierAddr(String supplierAddr) {
        this.supplierAddr = supplierAddr;
    }

    /**
     * 获取"供货商地址"
     */
    public String getSupplierAddr() {
        return supplierAddr;
    }

    /**
     * 设置"供货商邮箱"
     */
    public void setSupplierMailbox(String supplierMailbox) {
        this.supplierMailbox = supplierMailbox;
    }

    /**
     * 获取"供货商邮箱"
     */
    public String getSupplierMailbox() {
        return supplierMailbox;
    }


}

