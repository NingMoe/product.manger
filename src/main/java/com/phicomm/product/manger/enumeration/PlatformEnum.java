package com.phicomm.product.manger.enumeration;

import com.google.common.base.Strings;

/**
 * 平台
 *
 * @author wei.yang on 2017/12/29
 */
public enum PlatformEnum {

    /**
     * IOS
     */
    IOS("ios"),

    /**
     * android
     */
    ANDROID("android");

    private String value;

    PlatformEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * 看看存在不存在某个key
     *
     * @param value val
     * @return 是否存在
     */
    public static boolean exist(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return false;
        }
        for (PlatformEnum platformEnum : PlatformEnum.values()) {
            if (platformEnum.value.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
