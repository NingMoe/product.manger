package com.phicomm.product.manger.model.server;

/**
 * 拆分一下HostAndPort
 * Created by wei.yang on 2017/7/13.
 */
public class BalanceOtaServerDetailBean {

    private int id;

    private int port;

    private String ip;

    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BalanceOtaServerDetailBean{" +
                "id=" + id +
                ", port=" + port +
                ", ip='" + ip + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
