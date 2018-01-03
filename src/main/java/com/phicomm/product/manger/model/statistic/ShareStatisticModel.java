package com.phicomm.product.manger.model.statistic;

/**
 * 分享模块model
 *
 * @author song02.cao
 */
public class ShareStatisticModel {
    private String shareType;
    private String shareDate;
    private long shareCount;

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getShareDate() {
        return shareDate;
    }

    public void setShareDate(String shareDate) {
        this.shareDate = shareDate;
    }

    public long getShareCount() {
        return shareCount;
    }

    public void setShareCount(long shareCount) {
        this.shareCount = shareCount;
    }

    @Override
    public String toString() {
        return "ShareStatisticModel{" +
                "shareType='" + shareType + '\'' +
                ", shareDate='" + shareDate + '\'' +
                ", shareCount=" + shareCount +
                '}';
    }
}
