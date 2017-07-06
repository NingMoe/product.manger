package com.phicomm.product.manger.model.table;

public class FeedbackInfo {

    private Long id;

    private String userId;

    private String appId;

    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FeedbackInfo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", appId='" + appId + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}