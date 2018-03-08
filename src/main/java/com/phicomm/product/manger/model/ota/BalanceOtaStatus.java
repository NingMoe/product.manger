package com.phicomm.product.manger.model.ota;

/**
 * 核心信息
 *
 * @author wei.yang on 2017/7/11.
 */
public class BalanceOtaStatus {

    private String firmwareType;

    private String production;

    private String environment;

    private int version;

    private int testing;

    private int enable;

    public String getFirmwareType() {
        return firmwareType;
    }

    public void setFirmwareType(String firmwareType) {
        this.firmwareType = firmwareType;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

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
                "firmwareType='" + firmwareType + '\'' +
                ", production='" + production + '\'' +
                ", environment='" + environment + '\'' +
                ", version=" + version +
                ", testing=" + testing +
                ", enable=" + enable +
                '}';
    }
}
