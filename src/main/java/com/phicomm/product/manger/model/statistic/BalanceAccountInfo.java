package com.phicomm.product.manger.model.statistic;

/**
 * 电子秤相关数量
 * Created by wei.yang on 2017/8/15.
 */
public class BalanceAccountInfo {

    private int memberCount;

    private int userCount;

    private int macCount;

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getMacCount() {
        return macCount;
    }

    public void setMacCount(int macCount) {
        this.macCount = macCount;
    }

    @Override
    public String toString() {
        return "BalanceCountInfo{" +
                "memberCount=" + memberCount +
                ", userCount=" + userCount +
                ", macCount=" + macCount +
                '}';
    }
}
