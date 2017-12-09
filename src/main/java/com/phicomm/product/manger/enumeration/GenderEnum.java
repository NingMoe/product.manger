package com.phicomm.product.manger.enumeration;

/**
 * gender
 *
 * @author wei.yang on 2017/9/4.
 */
public enum GenderEnum {

    /**
     * 男性
     */
    BOY(1),

    /**
     * 女性
     */
    GIRL(0);

    private int key;

    GenderEnum(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
