package com.phicomm.product.manger.model.statistic;

/**
 * 位置信息
 * Created by wei.yang on 2017/7/4.
 */
public class BalanceLocationBean {

    private String activeCountry;

    private String activeProvince;

    private String activeCity;

    private String activeCounty;

    public String getActiveCountry() {
        return activeCountry;
    }

    public void setActiveCountry(String activeCountry) {
        this.activeCountry = activeCountry;
    }

    public String getActiveProvince() {
        return activeProvince;
    }

    public void setActiveProvince(String activeProvince) {
        this.activeProvince = activeProvince;
    }

    public String getActiveCity() {
        return activeCity;
    }

    public void setActiveCity(String activeCity) {
        this.activeCity = activeCity;
    }

    public String getActiveCounty() {
        return activeCounty;
    }

    public void setActiveCounty(String activeCounty) {
        this.activeCounty = activeCounty;
    }

    @Override
    public String toString() {
        return "BalanceLocationBean{" +
                "activeCountry='" + activeCountry + '\'' +
                ", activeProvince='" + activeProvince + '\'' +
                ", activeCity='" + activeCity + '\'' +
                ", activeCounty='" + activeCounty + '\'' +
                '}';
    }
}
