package com.phicomm.product.manger.model.feedback;

import java.util.List;

/**
 * 反馈者基本设备信息和反馈意见基本信息
 *
 * @author wei.yang on 2017/10/30
 */
public class FeedbackWithDeviceInfo {

    private String appId;

    private String userId;

    private String feedback;

    private String platform;

    private String deviceType;

    private String appVersion;

    private String phoneNumber;

    private List<String> picture;

    private String systemVersion;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    @Override
    public String toString() {
        return "FeedbackWithDeviceInfo{" +
                "appId='" + appId + '\'' +
                ", userId='" + userId + '\'' +
                ", feedback='" + feedback + '\'' +
                ", platform='" + platform + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", picture=" + picture +
                ", systemVersion='" + systemVersion + '\'' +
                '}';
    }
}
