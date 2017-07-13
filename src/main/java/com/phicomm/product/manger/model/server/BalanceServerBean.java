package com.phicomm.product.manger.model.server;

/**
 * server信息
 * Created by wei.yang on 2017/7/13.
 */
public class BalanceServerBean {

    private int id;

    private String hostAndPort;

    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHostAndPort() {
        return hostAndPort;
    }

    public void setHostAndPort(String hostAndPort) {
        this.hostAndPort = hostAndPort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BalanceServerAddressBean{" +
                "id=" + id +
                ", hostAndPort='" + hostAndPort + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

}
