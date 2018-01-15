package com.phicomm.product.manger.model.trace;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 用户活跃度模型
 *
 * @author qiang.ren
 * @date 2018/1/10
 */
public class UserActivityInfo {

    private String appId;

    private String date;

    private String type;

    private String activityDate;

    private int total;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserActivityInfo{" +
                "appId='" + appId + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", activityDate='" + activityDate + '\'' +
                ", total=" + total +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }

    public UserActivityInfo(String appId, String date, String type, String activityDate, int total, Date updateTime, Date createTime) {
        this.appId = appId;
        this.date = date;
        this.type = type;
        this.activityDate = activityDate;
        this.total = total;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public UserActivityInfo() {
    }
}
