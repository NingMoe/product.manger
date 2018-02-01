package com.phicomm.product.manger.model.terminal;

/**
 * @author wei.yang on 2017/12/29
 */
public class BaseSearchEntity {

    private String platform;

    private String dateType;

    private String appId;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "BaseSearchEntity{" +
                "platform='" + platform + '\'' +
                ", dateType='" + dateType + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }
}
