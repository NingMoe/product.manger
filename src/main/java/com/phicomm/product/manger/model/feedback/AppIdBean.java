package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/11/9
 */
public class AppIdBean {

    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "AppIdBean{" +
                "appId='" + appId + '\'' +
                '}';
    }
}
