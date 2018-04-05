package com.phicomm.product.manger.model.mac;

/**
 * mac
 *
 * @author qinag.ren on 2018/3/28
 */
public class BalanceDeviceModel {

    private String sn;

    private String mac;

    private String type;

    public BalanceDeviceModel() {
    }

    public BalanceDeviceModel(String type) {
        this.type = type;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
