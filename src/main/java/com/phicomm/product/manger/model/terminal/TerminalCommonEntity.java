package com.phicomm.product.manger.model.terminal;

/**
 * 平台 && 渠道 && 版本号、网络、手机型号等   作为一个通用model
 *
 * @author wei.yang on 2017/12/28
 */
public class TerminalCommonEntity extends StatisticEntity {

    private String platform;

    private String appId;

    public String getPlatform() {
        return platform;
    }

    public TerminalCommonEntity setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public TerminalCommonEntity setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    @Override
    public String toString() {
        return "TerminalCommonEntity{" +
                "platform='" + platform + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }
}
