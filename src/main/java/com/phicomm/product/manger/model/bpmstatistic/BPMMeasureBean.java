package com.phicomm.product.manger.model.bpmstatistic;

/**
 *
 * Created by yafei.hou on 2017/11/8.
 */
public class BPMMeasureBean {

    private int measureCount;

    private String measureTime;

    public BPMMeasureBean() {
    }

    public BPMMeasureBean( String measureTime,int measureCount) {
        this.measureCount = measureCount;
        this.measureTime = measureTime;
    }

    public int getMeasureCount() {
        return measureCount;
    }

    public void setMeasureCount(int measureCount) {
        this.measureCount = measureCount;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }

    @Override
    public String toString() {
        return "BPMMeasureBean{" +
                "measureCount=" + measureCount +
                ", measureTime='" + measureTime + '\'' +
                '}';
    }
}
