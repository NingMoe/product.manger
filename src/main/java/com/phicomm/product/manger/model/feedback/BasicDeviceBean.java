package com.phicomm.product.manger.model.feedback;

/**
 * 设备信息
 *
 * @author wei.yang on 2017/10/30
 */
public class BasicDeviceBean {

    private String platform;

    private String deviceType;

    private String appVersion;

    private String phoneNumber;

    private String systemVersion;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    @Override
    public String toString() {
        return "BasicDeviceBean{" +
                "platform='" + platform + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", systemVersion='" + systemVersion + '\'' +
                '}';
    }
}
