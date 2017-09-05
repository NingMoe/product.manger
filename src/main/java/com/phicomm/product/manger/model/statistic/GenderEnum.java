package com.phicomm.product.manger.model.statistic;

/**
 * gender
 * Created by wei.yang on 2017/9/4.
 */
public enum GenderEnum {

    BOY(1),

    GIRL(0);

    private int key;

    GenderEnum(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
