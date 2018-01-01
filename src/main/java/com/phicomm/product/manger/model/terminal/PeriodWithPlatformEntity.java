package com.phicomm.product.manger.model.terminal;

import java.util.Date;

/**
 * @author wei.yang on 2017/12/29
 */
public class PeriodWithPlatformEntity extends BaseSearchEntity {

    private Date startTime;

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
