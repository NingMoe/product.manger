package com.phicomm.product.manger.module.fota;

import com.google.common.base.Strings;
import com.phicomm.product.manger.enumeration.FirmwareEnvironmentEnum;
import com.phicomm.product.manger.model.firmware.FirmwareInfo;

import javax.validation.constraints.NotNull;

/**
 * 固件升级的上下文
 * <p>
 * Created by yufei.liu on 2017/7/12.
 */
public class FirmwareUpgradeContext {

    /**
     * 固件类型
     */
    private String firmwareType;

    /**
     * 硬件版本
     */
    private String hardwareCode;

    /**
     * 升级的环境
     */
    private FirmwareEnvironmentEnum firmwareEnvironmentEnum;

    /**
     * 版本号
     */
    private int versionCode;

    /**
     * 固件信息
     */
    private FirmwareInfo firmwareInfo;

    /**
     * 参数
     */
    private String param;

    public FirmwareUpgradeContext(String firmwareType,
                                  String hardwareCode,
                                  FirmwareEnvironmentEnum firmwareEnvironmentEnum,
                                  int versionCode,
                                  FirmwareInfo firmwareInfo,
                                  String param) {
        this.firmwareType = firmwareType;
        this.hardwareCode = hardwareCode;
        this.firmwareEnvironmentEnum = firmwareEnvironmentEnum;
        this.versionCode = versionCode;
        this.firmwareInfo = firmwareInfo;
        this.param = param;
    }

    /**
     * 获取固件类型
     */
    public String getFirmwareType() {
        return firmwareType;
    }

    /**
     * 判断是否是测试环境
     */
    public boolean isTestEnvironment() {
        return firmwareEnvironmentEnum.getEnvironment().equals("test");
    }

    /**
     * 判断是否是生产环境
     */
    public boolean isProdEnvironment() {
        return firmwareEnvironmentEnum.getEnvironment().equals("prod");
    }

    /**
     * 获取输入参数
     */
    @NotNull
    public String getParam() {
        return Strings.nullToEmpty(param);
    }

    @Override
    public String toString() {
        return "FirmwareUpgradeContext{" +
                "firmwareType='" + firmwareType + '\'' +
                ", hardwareCode='" + hardwareCode + '\'' +
                ", firmwareEnvironmentEnum=" + firmwareEnvironmentEnum +
                ", versionCode=" + versionCode +
                ", firmwareInfo=" + firmwareInfo +
                ", param='" + param + '\'' +
                '}';
    }
}
