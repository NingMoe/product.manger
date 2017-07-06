package com.phicomm.product.manger.model.table;

import java.util.Arrays;

public class FeedbackInfoWithBLOBs extends FeedbackInfo {

    private String headerUrl;

    private String username;

    private String feedback;

    private String picture;

    private String[] imageUrl;

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "FeedbackInfoWithBLOBs{" +
                "headerUrl='" + headerUrl + '\'' +
                ", username='" + username + '\'' +
                ", feedback='" + feedback + '\'' +
                ", picture='" + picture + '\'' +
                ", imageUrl=" + Arrays.toString(imageUrl) +
                '}';
    }
}