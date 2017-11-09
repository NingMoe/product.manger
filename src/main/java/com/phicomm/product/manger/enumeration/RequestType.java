package com.phicomm.product.manger.enumeration;

/**
 * @author wei.yang on 2017/10/21 11:15.
 */
public enum RequestType {

    /**
     * POST请求
     */
    POST("POST"),

    /**
     * get请求
     */
    GET("GET");

    private String keyName;

    RequestType(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
}
