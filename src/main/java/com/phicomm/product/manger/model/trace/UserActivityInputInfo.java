package com.phicomm.product.manger.model.trace;

/**
 * 用户活跃度传入信息
 *
 * @author qiang.ren
 * @date 2018/1/2
 */
public class UserActivityInputInfo {

    private String userId;

    private String date;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserActivityInputInfo{" +
                "userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
