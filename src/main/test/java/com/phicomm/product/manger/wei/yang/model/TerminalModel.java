package com.phicomm.product.manger.wei.yang.model;

/**
 * @author wei.yang on 2017/12/28
 */
public class TerminalModel {

    private String platform;

    private String channel;

    private int count;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TerminalModel{" +
                "platform='" + platform + '\'' +
                ", channel='" + channel + '\'' +
                ", count=" + count +
                '}';
    }
}
