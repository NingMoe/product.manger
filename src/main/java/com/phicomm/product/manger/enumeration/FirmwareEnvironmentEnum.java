package com.phicomm.product.manger.enumeration;

/**
 * 固件的使用的环境
 * <p>
 * Created by yufei.liu on 2017/7/10.
 */
public enum FirmwareEnvironmentEnum {

    TEST("test"),

    PROD("prod");

    private String environment;

    FirmwareEnvironmentEnum(String environment) {
        this.environment = environment;
    }
}
