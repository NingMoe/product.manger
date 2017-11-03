package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/10/30
 */
public class FeedbackStatusBean {

    private String sessionId;

    private String lockUserId;

    private Long lockTimeStamp;

    private String dialogStatus;

    public String getSessionId() {
        return sessionId;
    }

    public FeedbackStatusBean setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getLockUserId() {
        return lockUserId;
    }

    public FeedbackStatusBean setLockUserId(String lockUserId) {
        this.lockUserId = lockUserId;
        return this;
    }

    public Long getLockTimeStamp() {
        return lockTimeStamp;
    }

    public FeedbackStatusBean setLockTimeStamp(Long lockTimeStamp) {
        this.lockTimeStamp = lockTimeStamp;
        return this;
    }

    public String getDialogStatus() {
        return dialogStatus;
    }

    public FeedbackStatusBean setDialogStatus(String dialogStatus) {
        this.dialogStatus = dialogStatus;
        return this;
    }

    @Override
    public String toString() {
        return "FeedbackStatusBean{" +
                "sessionId='" + sessionId + '\'' +
                ", lockUserId='" + lockUserId + '\'' +
                ", lockTimeStamp=" + lockTimeStamp +
                ", dialogStatus='" + dialogStatus + '\'' +
                '}';
    }
}
