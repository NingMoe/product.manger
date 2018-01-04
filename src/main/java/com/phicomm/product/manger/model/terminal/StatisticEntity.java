package com.phicomm.product.manger.model.terminal;

/**
 * 平台 && 渠道 && 版本号、网络、手机型号等   作为一个通用model
 *
 * @author wei.yang on 2017/12/29
 */
public class StatisticEntity {

    /**
     * 含义包含版本号、网络状态、手机型号等等一系列对比对象
     */
    private String compareObject;

    private String createTime;

    private int count;

    public String getCompareObject() {
        return compareObject;
    }

    public void setCompareObject(String compareObject) {
        this.compareObject = compareObject;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "StatisticEntity{" +
                "compareObject='" + compareObject + '\'' +
                ", createTime='" + createTime + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
