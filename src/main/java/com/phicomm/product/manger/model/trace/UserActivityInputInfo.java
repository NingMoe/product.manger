package com.phicomm.product.manger.model.trace;

/**
 * 用户活跃度传入信息
 *
 * @author qiang.ren
 * @date 2018/1/2
 */
public class UserActivityInputInfo {

    private String userId;

    private String date;

    private String appId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "UserActivityInputInfo{" +
                "userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }
}
