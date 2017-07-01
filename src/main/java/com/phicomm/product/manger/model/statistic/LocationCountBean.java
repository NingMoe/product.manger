package com.phicomm.product.manger.model.statistic;

/**
 * 位置信息
 * Created by wei.yang on 2017/6/16.
 */
public class LocationCountBean {

    private String province;

    private int generateCount;

    public int getGenerateCount() {
        return generateCount;
    }

    public void setGenerateCount(int generateCount) {
        this.generateCount = generateCount;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "LocationCountBean{" +
                "province='" + province + '\'' +
                ", generateCount=" + generateCount +
                '}';
    }
}
