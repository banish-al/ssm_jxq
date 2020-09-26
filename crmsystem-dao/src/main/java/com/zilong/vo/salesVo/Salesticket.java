package com.zilong.vo.salesVo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zilong.vo.systemVo.Users;

import java.io.Serializable;

/**
 * 表名 :  salesticket<br/>
 * 表注释 : 销售单表
 */
@JsonIgnoreProperties(value = "handler")
public class Salesticket implements Serializable {

    /**
     * 销售单id
     */
    private Integer salesticketId;
    /**
     * 销售单订单id
     */
    private Salesorder salesorder;
    /**
     * 销售人员id
     */
    private Users users;
    /**
     * 入单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date salesticketDate;
    /**
     * 处理结果
     */
    private String salesticketState;
    /**
     * 备注
     */
    private String salesticketText;

    public Salesticket() {
        super();
    }

    public Salesticket(Integer salesticketId, Salesorder salesorder, Users users, Date salesticketDate, String salesticketState, String salesticketText) {
        this.salesticketId = salesticketId;
        this.salesorder = salesorder;
        this.users = users;
        this.salesticketDate = salesticketDate;
        this.salesticketState = salesticketState;
        this.salesticketText = salesticketText;
    }

    public Integer getSalesticketId() {
        return salesticketId;
    }

    public void setSalesticketId(Integer salesticketId) {
        this.salesticketId = salesticketId;
    }

    public Salesorder getSalesorder() {
        return salesorder;
    }

    public void setSalesorder(Salesorder salesorder) {
        this.salesorder = salesorder;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Date getSalesticketDate() {
        return salesticketDate;
    }

    public void setSalesticketDate(Date salesticketDate) {
        this.salesticketDate = salesticketDate;
    }

    public String getSalesticketState() {
        return salesticketState;
    }

    public void setSalesticketState(String salesticketState) {
        this.salesticketState = salesticketState;
    }

    public String getSalesticketText() {
        return salesticketText;
    }

    public void setSalesticketText(String salesticketText) {
        this.salesticketText = salesticketText;
    }

    @Override
    public String toString() {
        return "Salesticket{" +
                "salesticketId=" + salesticketId +
                ", salesorder=" + salesorder +
                ", users=" + users +
                ", salesticketDate=" + salesticketDate +
                ", salesticketState='" + salesticketState + '\'' +
                ", salesticketText='" + salesticketText + '\'' +
                '}';
    }
}

