package com.phicomm.product.manger.model.terminal;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author wei.yang on 2017/12/29
 */
public class PeriodWithPlatformEntity extends BaseSearchEntity {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC+8")
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PeriodWithPlatformEntity{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
