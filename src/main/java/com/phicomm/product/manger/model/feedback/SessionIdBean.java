package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/10/30
 */
public class SessionIdBean {

    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public SessionIdBean setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    @Override
    public String toString() {
        return "SessionIdBean{" +
                "sessionId='" + sessionId + '\'' +
                '}';
    }
}
