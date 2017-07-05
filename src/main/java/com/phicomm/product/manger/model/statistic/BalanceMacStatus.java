package com.phicomm.product.manger.model.statistic;

/**
 * 电子秤信息
 * Created by wei.yang on 2017/7/3.
 */
public class BalanceMacStatus {

    private String activeLocation;

    private int memberCount;

    private String createTime;

    public String getActiveLocation() {
        return activeLocation;
    }

    public BalanceMacStatus setActiveLocation(String activeLocation) {
        this.activeLocation = activeLocation;
        return this;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public BalanceMacStatus setMemberCount(int memberCount) {
        this.memberCount = memberCount;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public BalanceMacStatus setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public String toString() {
        return "BalanceMacStatus{" +
                "activeLocation='" + activeLocation + '\'' +
                ", memberCount=" + memberCount +
                ", createTime=" + createTime +
                '}';
    }
}
