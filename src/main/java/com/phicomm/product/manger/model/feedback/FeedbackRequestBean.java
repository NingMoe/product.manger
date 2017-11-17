package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/10/30
 */
public class FeedbackRequestBean {

    private String userId;

    private String appId;

    private String sessionId;

    private String deviceType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String toString() {
        return "FeedbackRequestBean{" +
                "userId='" + userId + '\'' +
                ", appId='" + appId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }
}
