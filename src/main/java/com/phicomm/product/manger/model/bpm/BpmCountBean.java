package com.phicomm.product.manger.model.bpm;

/**
 * 每天被绑定的血压计的数量
 * Created by yafei.hou on 2017/11/6.
 */
public class BpmCountBean {

    private int bindPBMCount;

    private String bindPBMTime;

    public BpmCountBean() {
    }

    public BpmCountBean(int bindPBMCount, String bindPBMTime) {
        this.bindPBMCount = bindPBMCount;
        this.bindPBMTime = bindPBMTime;
    }

    public int getBindPBMCount() {
        return bindPBMCount;
    }

    public void setBindPBMCount(int bindPBMCount) {
        this.bindPBMCount = bindPBMCount;
    }

    public String getBindPBMTime() {
        return bindPBMTime;
    }

    public void setBindPBMTime(String bindPBMTime) {
        this.bindPBMTime = bindPBMTime;
    }

    @Override
    public String toString() {
        return "BpmCountBean{" +
                "bindPBMCount=" + bindPBMCount +
                ", bindPBMTime='" + bindPBMTime + '\'' +
                '}';
    }
}
