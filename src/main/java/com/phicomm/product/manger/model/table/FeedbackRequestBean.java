package com.phicomm.product.manger.model.table;

import java.util.Date;

/**
 * 请求查找指定反馈意见的参数
 * Created by wei.yang on 2017/7/6.
 */
public class FeedbackRequestBean {

    private int pageSize;

    private int startId;

    private String appType;

    private Date startTime;

    private Date endTime;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartId() {
        return startId;
    }

    public void setStartId(int startId) {
        this.startId = startId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "FeedbackRequestBean{" +
                "pageSize=" + pageSize +
                ", startId=" + startId +
                ", appType='" + appType + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
