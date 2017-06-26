package com.phicomm.product.manger.enumeration;

/**
 * 功能枚举
 * Created by yufei.liu on 2017/5/31.
 */
public enum PermissionEnum {

    /**
     * 所有人都有的权限
     */
    COMMON("common"),

    /**
     * 特定接口
     */
    SPECIAL("special");

    private String value;

    PermissionEnum(String value) {
        this.value = value;
    }
}
