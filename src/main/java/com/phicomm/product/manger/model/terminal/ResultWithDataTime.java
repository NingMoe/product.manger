package com.phicomm.product.manger.model.terminal;

/**
 * 数据展示的横轴时间
 *
 * @author wei.yang on 2018/1/3
 */
public class ResultWithDataTime extends BaseResultEntity {

    private String dataTime;

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    @Override
    public String toString() {
        return "ResultWithDataTime{" +
                "dataTime='" + dataTime + '\'' +
                '}';
    }
}
