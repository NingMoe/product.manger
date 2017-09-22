package com.phicomm.product.manger.model.picture;

/**
 * Created by xiang.zhang on 2017/9/15.
 */
public class PictureUploadParamConfig {
    private String param;

    public String getParam() {

        return param;
    }

    public void setParam(String param) {

        this.param = param == null ? null : param.trim();
    }

    @Override
    public String toString() {
        return "PictureUploadParamConfig{" +
                "param='" + param + '\'' +
                '}';
    }
}
