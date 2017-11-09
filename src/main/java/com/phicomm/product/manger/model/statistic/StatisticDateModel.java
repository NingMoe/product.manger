package com.phicomm.product.manger.model.statistic;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 统计某一天
 *
 * @author yufei.liu
 */
public class StatisticDateModel {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
