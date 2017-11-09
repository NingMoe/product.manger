package com.phicomm.product.manger.model.feedback;

import java.util.Date;

/**
 * 基础反馈信息
 *
 * @author wei.yang on 2017/10/30
 */
public class BasicFeedbackBean {

    private String appId;

    private String userId;

    private Date createTime;

    private String sessionId;

    private String feedbackStatus;

    private String sessionSubject;

    private String lockUserId;

    private Long lockTimeStamp;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public String getSessionSubject() {
        return sessionSubject;
    }

    public void setSessionSubject(String sessionSubject) {
        this.sessionSubject = sessionSubject;
    }

    public String getLockUserId() {
        return lockUserId;
    }

    public void setLockUserId(String lockUserId) {
        this.lockUserId = lockUserId;
    }

    public Long getLockTimeStamp() {
        return lockTimeStamp;
    }

    public void setLockTimeStamp(Long lockTimeStamp) {
        this.lockTimeStamp = lockTimeStamp;
    }

    @Override
    public String toString() {
        return "BasicFeedbackBean{" +
                "appId='" + appId + '\'' +
                ", userId='" + userId + '\'' +
                ", createTime=" + createTime +
                ", sessionId='" + sessionId + '\'' +
                ", feedbackStatus='" + feedbackStatus + '\'' +
                ", sessionSubject='" + sessionSubject + '\'' +
                ", lockUserId='" + lockUserId + '\'' +
                ", lockTimeStamp=" + lockTimeStamp +
                '}';
    }
}
