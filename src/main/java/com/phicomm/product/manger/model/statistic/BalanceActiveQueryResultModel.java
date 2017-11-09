package com.phicomm.product.manger.model.statistic;

/**
 * 体脂秤活跃查询结果集
 *
 * @author yufei.liu
 */
public class BalanceActiveQueryResultModel {

    private String[] dates;

    private int[] pvs;

    private int[] uvs;

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }

    public int[] getPvs() {
        return pvs;
    }

    public void setPvs(int[] pvs) {
        this.pvs = pvs;
    }

    public int[] getUvs() {
        return uvs;
    }

    public void setUvs(int[] uvs) {
        this.uvs = uvs;
    }
}
