package com.phicomm.product.manger.model.server;

/**
 * address
 * Created by wei.yang on 2017/4/7.
 */
public class BalanceServerAddressBean {

    private String hostAndPort;

    public String getHostAndPort() {
        return hostAndPort;
    }

    public void setHostAndPort(String hostAndPort) {
        this.hostAndPort = hostAndPort;
    }

    @Override
    public String toString() {
        return "BalanceServerAddressBean{" +
                "hostAndPort='" + hostAndPort + '\'' +
                '}';
    }
}
