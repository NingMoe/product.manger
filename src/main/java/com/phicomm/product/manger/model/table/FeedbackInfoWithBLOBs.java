package com.phicomm.product.manger.model.table;

public class FeedbackInfoWithBLOBs extends FeedbackInfo {
    private String feedback;

    private String picture;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback == null ? null : feedback.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }
}