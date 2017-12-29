package com.phicomm.product.manger.enumeration;

import com.google.common.base.Strings;

/**
 * 设备信息
 *
 * @author wei.yang on 2017/12/29
 */
public enum TerminalDataTypeEnum {

    /**
     * 渠道
     */
    CHANNEL("channel", "equipmentTerminalInfo.appInfo.channel"),

    /**
     * 手机机型
     */
    MOBILE_MODEL("mobile_model", "equipmentTerminalInfo.systemInfo.mobileModel"),

    /**
     * 手机版本号
     */
    MOBILE_VERSION("mobile_version", "equipmentTerminalInfo.systemInfo.systemVersionCode"),

    /**
     * 网络运营商
     */
    NETWORK_OPERATOR("network_operator", "equipmentTerminalInfo.systemInfo.networkOperator"),

    /**
     * 网络类型
     */
    NETWORK_TYPE("network_type", "equipmentTerminalInfo.systemInfo.networkType");

    private String dataType;

    private String mongoKey;

    TerminalDataTypeEnum(String dataType, String mongoKey) {
        this.dataType = dataType;
        this.mongoKey = mongoKey;
    }

    public String getDataType() {
        return dataType;
    }

    public String getMongoKey() {
        return mongoKey;
    }

    /**
     * 看存在不存在，不存在就不支持
     *
     * @param value 要看的值
     * @return 是否存在
     */
    public static boolean exist(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return false;
        }
        for (TerminalDataTypeEnum dataTypeEnum : TerminalDataTypeEnum.values()) {
            if (dataTypeEnum.dataType.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
