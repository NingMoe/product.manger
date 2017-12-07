package com.phicomm.product.manger.enumeration;

/**
 * @author wei.yang on 2017/12/7
 */
public enum EnvironmentEnum {

    /**
     * 生产环境
     */
    PROD("prod"),

    /**
     * 测试环境
     */
    TEST("test"),

    /**
     * 本地环境
     */
    LOCAL("local");

    private String value;

    EnvironmentEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
