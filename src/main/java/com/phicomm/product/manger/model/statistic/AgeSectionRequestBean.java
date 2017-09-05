package com.phicomm.product.manger.model.statistic;

/**
 * 查询
 * Created by wei.yang on 2017/9/4.
 */
public class AgeSectionRequestBean {

    private String type;

    private int gender;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "AgeSectionRequestBean{" +
                "type='" + type + '\'' +
                ", gender=" + gender +
                '}';
    }
}
