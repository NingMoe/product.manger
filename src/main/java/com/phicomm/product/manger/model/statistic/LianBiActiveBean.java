package com.phicomm.product.manger.model.statistic;

/**
 * 联璧激活信息
 * Created by wei.yang on 2017/7/11.
 */
public class LianBiActiveBean extends BalanceLocationBean{

    private String phoneNumber;

    private String kCode;

    private String ip;

    private String mac;

    private String sn;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getkCode() {
        return kCode;
    }

    public void setkCode(String kCode) {
        this.kCode = kCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "LianBiActiveBean{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", kCode='" + kCode + '\'' +
                ", ip='" + ip + '\'' +
                ", mac='" + mac + '\'' +
                ", sn='" + sn + '\'' +
                '}';
    }
}
