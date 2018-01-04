package com.phicomm.product.manger.model.terminal;

/**
 * @author wei.yang on 2018/1/1
 */
public class BaseResultEntity {

    private String compareObject;

    private int count;

    public String getCompareObject() {
        return compareObject;
    }

    public void setCompareObject(String compareObject) {
        this.compareObject = compareObject;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BaseResultEntity{" +
                "compareObject='" + compareObject + '\'' +
                ", count=" + count +
                '}';
    }
}
