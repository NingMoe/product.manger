package com.phicomm.product.manger.model.feedback;

import java.util.Date;

/**
 * @author wei.yang on 2017/11/8
 */
public class HistoryRequestWithPage {

    private String deviceType;

    private String softVersion;

    private Date startTime;

    private Integer page;

    private Date endTime;

    private String appId;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
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
        return "HistoryRequestWithPage{" +
                "deviceType='" + deviceType + '\'' +
                ", softVersion='" + softVersion + '\'' +
                ", startTime=" + startTime +
                ", page=" + page +
                ", endTime=" + endTime +
                ", appId='" + appId + '\'' +
                '}';
    }
}
