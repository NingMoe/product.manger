package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/11/2
 */
public class NewFeedbackCountBean {

    private int newFeedbackCount;

    private int totalCount;

    public int getNewFeedbackCount() {
        return newFeedbackCount;
    }

    public void setNewFeedbackCount(int newFeedbackCount) {
        this.newFeedbackCount = newFeedbackCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "NewFeedbackCountBean{" +
                "newFeedbackCount=" + newFeedbackCount +
                ", totalCount=" + totalCount +
                '}';
    }
}
