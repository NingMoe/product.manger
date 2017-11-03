package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/10/31
 */
public class DialogWithSessionId extends DialogBean {

    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "DialogWithSessionId{" +
                "sessionId='" + sessionId + '\'' +
                '}';
    }
}
