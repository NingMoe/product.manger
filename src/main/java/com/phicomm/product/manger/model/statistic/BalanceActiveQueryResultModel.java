package com.phicomm.product.manger.model.statistic;

/**
 * 体脂秤活跃查询结果集
 *
 * @author yufei.liu
 */
public class BalanceActiveQueryResultModel {

    private String[] dates;

    private int[] values;

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }
}
