package com.phicomm.product.manger.model.ota;

/**
 * 核心信息
 * Created by wei.yang on 2017/7/11.
 */
public class BalanceOtaStatus {

    private String environment;

    private int version;

    private int testing;

    private int enable;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getTesting() {
        return testing;
    }

    public void setTesting(int testing) {
        this.testing = testing;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "BalanceOtaStatus{" +
                "environment='" + environment + '\'' +
                ", version=" + version +
                ", testing=" + testing +
                ", enable=" + enable +
                '}';
    }
}
