package com.phicomm.product.manger.model.mcu;

/**
 * mcu
 * Created by wei.yang on 2017/7/17.
 */
public class BalanceMcuBean {

    private int id;

    private int version;

    private String fileUrl;

    private int crc;

    private String testing;

    private String enable;

    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getCrc() {
        return crc;
    }

    public void setCrc(int crc) {
        this.crc = crc;
    }

    public String getTesting() {
        return testing;
    }

    public void setTesting(String testing) {
        this.testing = testing;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BalanceMcuBean{" +
                "id=" + id +
                ", version=" + version +
                ", fileUrl='" + fileUrl + '\'' +
                ", crc=" + crc +
                ", testing='" + testing + '\'' +
                ", enable='" + enable + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
