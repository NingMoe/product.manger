package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/10/31
 */
public class FeedbackStatusRequestBean {

    private String appId;

    private String sessionId;

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

    @Override
    public String toString() {
        return "FeedbackStatusRequestBean{" +
                "appId='" + appId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
