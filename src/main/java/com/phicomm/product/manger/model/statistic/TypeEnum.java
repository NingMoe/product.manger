package com.phicomm.product.manger.model.statistic;

/**
 * 类型
 * Created by wei.yang on 2017/9/4.
 */
public enum TypeEnum {

    /**
     * user_info
     */
    USER("user"),

    /**
     * balance_user_manager_info:member
     */
    MEMBER("member");

    private String keyName;

    TypeEnum(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
}
