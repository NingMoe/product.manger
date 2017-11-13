package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/11/7
 */
public class LockRequestBean {

    private String sessionId;

    private String userId;

    public String getSessionId() {
        return sessionId;
    }

    public LockRequestBean setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public LockRequestBean setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "LockRequestBean{" +
                "sessionId='" + sessionId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
