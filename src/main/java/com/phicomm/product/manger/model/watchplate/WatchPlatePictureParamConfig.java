package com.phicomm.product.manger.model.watchplate;

/**
 * Created by xiang.zhang on 2017/9/15.
 */
public class WatchPlatePictureParamConfig {
    private String param;

    public String getParam() {

        return param;
    }

    public void setParam(String param) {

        this.param = param == null ? null : param.trim();
    }

    @Override
    public String toString() {
        return "WatchPlatePictureParamConfig{" +
                "param='" + param + '\'' +
                '}';
    }
}
