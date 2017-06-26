package com.phicomm.product.manger.model.sync;

import java.util.List;

/**
 * 反馈信息列表
 * Created by wei.yang on 2017/6/1.
 */
public class Data<T> {

    private int pageSize;

    private long nextPageStartId;

    private List<T> feedbackList;

    public int getPageSize() {
        return pageSize;
    }

    public Data<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public long getNextPageStartId() {
        return nextPageStartId;
    }

    public Data<T> setNextPageStartId(long nextPageStartTimestamp) {
        this.nextPageStartId = nextPageStartTimestamp;
        return this;
    }

    public List<T> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<T> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @Override
    public String toString() {
        return "Data{" +
                "pageSize=" + pageSize +
                ", nextPageStartId=" + nextPageStartId +
                ", feedbackList=" + feedbackList +
                '}';
    }
}
