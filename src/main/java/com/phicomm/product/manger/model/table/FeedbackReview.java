package com.phicomm.product.manger.model.table;

import org.joda.time.DateTime;

/**
 * Created by Qiang on 2017/7/13.
 */
public class FeedbackReview {
    private Long id;
    private Long userId;
    private Long feedbackInfoId;
    private String reply;
    private String createTime;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getFeedbackInfoId() {
        return feedbackInfoId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFeedbackInfoId(Long feedbackInfoId) {
        this.feedbackInfoId = feedbackInfoId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public FeedbackReview(Long userId, Long feedbackInfoId, String reply, String createTime) {
        this.userId = userId;
        this.feedbackInfoId = feedbackInfoId;
        this.reply = reply;
        this.createTime = createTime;
    }

    public FeedbackReview() {
    }
}
