package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/11/9
 */
public class TerminalBean {

    private String deviceType;

    private String softVersion;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    @Override
    public String toString() {
        return "TerminalBean{" +
                "deviceType='" + deviceType + '\'' +
                ", softVersion='" + softVersion + '\'' +
                '}';
    }
}
