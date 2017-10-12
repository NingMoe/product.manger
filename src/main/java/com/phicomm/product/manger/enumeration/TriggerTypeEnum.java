package com.phicomm.product.manger.enumeration;

/**
 * 触发类型
 * Created by wei.yang on 2017/10/11.
 */
public enum  TriggerTypeEnum {

    /**
     * ota触发
     */
    OTA("ota"),

    /**
     * mcu触发
     */
    MCU("mcu");

    private String keyName;

    TriggerTypeEnum(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
}
