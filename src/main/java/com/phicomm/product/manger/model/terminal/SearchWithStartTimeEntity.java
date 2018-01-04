package com.phicomm.product.manger.model.terminal;

import java.util.Date;

/**
 * @author wei.yang on 2018/1/1
 */
public class SearchWithStartTimeEntity extends BaseSearchEntity {

    private Date startTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "SearchWithStartTimeEntity{" +
                "startTime=" + startTime +
                '}';
    }
}
