package com.phicomm.product.manger.model.terminal;

/**
 * @author wei.yang on 2017/12/29
 */
public class PageWithPlatformEntity extends PageEntity {

    private String platform;

    private String type;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PageWithPlatformEntity{" +
                "platform='" + platform + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
