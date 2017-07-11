package com.phicomm.product.manger.model.firmware;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FirmwareInfo {

    private Integer id;

    private String firmwareType;

    private String version;

    private Integer versionCode;

    private String environment;

    private String hardwareCode;

    private Integer enable;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String url;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirmwareType() {
        return firmwareType;
    }

    public void setFirmwareType(String firmwareType) {
        this.firmwareType = firmwareType == null ? null : firmwareType.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment == null ? null : environment.trim();
    }

    public String getHardwareCode() {
        return hardwareCode;
    }

    public void setHardwareCode(String hardwareCode) {
        this.hardwareCode = hardwareCode == null ? null : hardwareCode.trim();
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "FirmwareInfo{" +
                "firmwareType='" + firmwareType + '\'' +
                ", version='" + version + '\'' +
                ", versionCode=" + versionCode +
                ", environment='" + environment + '\'' +
                ", hardwareCode='" + hardwareCode + '\'' +
                ", enable=" + enable +
                ", createTime=" + createTime +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}