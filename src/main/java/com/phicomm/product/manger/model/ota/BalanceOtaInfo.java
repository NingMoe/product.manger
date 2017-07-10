package com.phicomm.product.manger.model.ota;

/**
 * balance ota
 * Created by wei.yang on 2017/7/10.
 */
public class BalanceOtaInfo {

    private int softwareVersion;

    private String aVersionFileUrl;

    private int aVersionCrc;

    private String bVersionFileUrl;

    private int bVersionCrc;

    private int testing;

    public int getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(int softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getaVersionFileUrl() {
        return aVersionFileUrl;
    }

    public void setaVersionFileUrl(String aVersionFileUrl) {
        this.aVersionFileUrl = aVersionFileUrl;
    }

    public int getaVersionCrc() {
        return aVersionCrc;
    }

    public void setaVersionCrc(int aVersionCrc) {
        this.aVersionCrc = aVersionCrc;
    }

    public String getbVersionFileUrl() {
        return bVersionFileUrl;
    }

    public void setbVersionFileUrl(String bVersionFileUrl) {
        this.bVersionFileUrl = bVersionFileUrl;
    }

    public int getbVersionCrc() {
        return bVersionCrc;
    }

    public void setbVersionCrc(int bVersionCrc) {
        this.bVersionCrc = bVersionCrc;
    }

    public int getTesting() {
        return testing;
    }

    public void setTesting(int testing) {
        this.testing = testing;
    }

    @Override
    public String toString() {
        return "BalanceOtaInfo{" +
                "softwareVersion=" + softwareVersion +
                ", aVersionFileUrl='" + aVersionFileUrl + '\'' +
                ", aVersionCrc=" + aVersionCrc +
                ", bVersionFileUrl='" + bVersionFileUrl + '\'' +
                ", bVersionCrc=" + bVersionCrc +
                ", testing=" + testing +
                '}';
    }
}
