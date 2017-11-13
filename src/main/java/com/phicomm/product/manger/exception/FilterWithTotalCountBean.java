package com.phicomm.product.manger.exception;

import com.phicomm.product.manger.model.feedback.FeedbackWithUserInfo;

import java.util.List;

/**
 * @author wei.yang on 2017/11/10
 */
public class FilterWithTotalCountBean {

    private List<FeedbackWithUserInfo> feedbackWithUserInfos;

    private Integer totalCount;

    public List<FeedbackWithUserInfo> getFeedbackWithUserInfos() {
        return feedbackWithUserInfos;
    }

    public FilterWithTotalCountBean setFeedbackWithUserInfos(List<FeedbackWithUserInfo> feedbackWithUserInfos) {
        this.feedbackWithUserInfos = feedbackWithUserInfos;
        return this;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public FilterWithTotalCountBean setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    @Override
    public String toString() {
        return "FilterWithTotalCountBean{" +
                "feedbackWithUserInfos=" + feedbackWithUserInfos +
                ", totalCount=" + totalCount +
                '}';
    }
}
