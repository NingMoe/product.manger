package com.phicomm.product.manger.model.feedback;

import java.util.Date;

/**
 * 获取历史反馈（用于后台管理）
 *
 * @author wei.yang on 2017/10/30
 */
public class HistoryRequestBean {

    private String deviceType;

    private Date startTime;

    private Integer maxId;

    private Date endTime;

    private String appId;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getMaxId() {
        return maxId;
    }

    public void setMaxId(Integer maxId) {
        this.maxId = maxId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "HistoryRequestBean{" +
                "deviceType='" + deviceType + '\'' +
                ", startTime=" + startTime +
                ", maxId=" + maxId +
                ", endTime=" + endTime +
                ", appId='" + appId + '\'' +
                '}';
    }
}
