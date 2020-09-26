package com.zilong.vo.dataVo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 表名 :  connection<br/>
 * 表注释 : 客户表
 */
@JsonIgnoreProperties(value = "handler")
public class Connection implements Serializable {

    /**
     * 客户id
     */
    private Integer connectionId;
    /**
     * 客户名字
     */
    private String connectionName;
    /**
     * 客户电话
     */
    private String connectionPhone;
    /**
     * 送货地址
     */
    private String connectionAddr;
    /**
     * 交易次数
     */
    private Integer connectionCount;

    // 状态  0爪巴   1还在
    private Integer connectionState;

    public Connection() {
        super();
    }

    public Connection(Integer connectionId, String connectionName, String connectionPhone, String connectionAddr, Integer connectionCount, Integer connectionState) {
        this.connectionId = connectionId;
        this.connectionName = connectionName;
        this.connectionPhone = connectionPhone;
        this.connectionAddr = connectionAddr;
        this.connectionCount = connectionCount;
        this.connectionState = connectionState;
    }

    public Integer getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Integer connectionId) {
        this.connectionId = connectionId;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getConnectionPhone() {
        return connectionPhone;
    }

    public void setConnectionPhone(String connectionPhone) {
        this.connectionPhone = connectionPhone;
    }

    public String getConnectionAddr() {
        return connectionAddr;
    }

    public void setConnectionAddr(String connectionAddr) {
        this.connectionAddr = connectionAddr;
    }

    public Integer getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(Integer connectionCount) {
        this.connectionCount = connectionCount;
    }

    public Integer getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(Integer connectionState) {
        this.connectionState = connectionState;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "connectionId=" + connectionId +
                ", connectionName='" + connectionName + '\'' +
                ", connectionPhone='" + connectionPhone + '\'' +
                ", connectionAddr='" + connectionAddr + '\'' +
                ", connectionCount=" + connectionCount +
                ", connectionState=" + connectionState +
                '}';
    }
}

