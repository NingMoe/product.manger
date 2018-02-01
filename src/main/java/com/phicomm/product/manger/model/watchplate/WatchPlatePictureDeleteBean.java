package com.phicomm.product.manger.model.watchplate;

import java.util.List;

/**
 * Created by xiang.zhang on 2018/1/23.
 * @author xiang.zhang
 */
public class WatchPlatePictureDeleteBean {

    String environment;

    List<WatchPlatePictureUpload> data;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public List<WatchPlatePictureUpload> getData() {
        return data;
    }

    public void setData(List<WatchPlatePictureUpload> data) {
        this.data = data;
    }

    public WatchPlatePictureDeleteBean() {
    }

    public WatchPlatePictureDeleteBean(String environment, List<WatchPlatePictureUpload> data) {
        this.environment = environment;
        this.data = data;
    }

    @Override
    public String toString() {
        return "WatchPlatePictureDeleteBean{" +
                "environment='" + environment + '\'' +
                ", data=" + data +
                '}';
    }
}
