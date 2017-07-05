package com.phicomm.product.manger.model.statistic;

/**
 * 上传数量
 * Created by wei.yang on 2017/3/30.
 */
public class CountBean {

    private int generateCount;

    private String generateTime;

    public int getGenerateCount() {
        return generateCount;
    }

    public void setGenerateCount(int generateCount) {
        this.generateCount = generateCount;
    }

    public String getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(String generateTime) {
        this.generateTime = generateTime;
    }

    @Override
    public String toString() {
        return "CountBean{" +
                "generateCount=" + generateCount +
                ", generateTime=" + generateTime +
                '}';
    }
}
