package com.phicomm.product.manger.enumeration;

/**
 * 固件的使用的环境
 * <p>
 * Created by yufei.liu on 2017/7/10.
 */
public enum FirmwareEnvironmentEnum {

    /**
     * 测试环境
     */
    TEST("test"),

    /**
     * 生产环境
     */
    PROD("prod");

    private String environment;

    FirmwareEnvironmentEnum(String environment) {
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }
}
