package com.phicomm.product.manger.caosong.mongo;

/**
 * Created by song02.cao on 2017/12/28.
 */
public class AppInfo {
    private String platform;
    private String channel;
    private long count;

//    public AppInfo(String platform, String channel, long count) {
//        this.platform = platform;
//        this.channel = channel;
//        this.count = count;
//    }

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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "platform='" + platform + '\'' +
                ", channel='" + channel + '\'' +
                ", count=" + count +
                '}';
    }
}
