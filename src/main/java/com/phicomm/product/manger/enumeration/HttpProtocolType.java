package com.phicomm.product.manger.enumeration;

/**
 * @author wei.yang on 2017/10/21 11:17.
 */
public enum HttpProtocolType {

    /**
     * http请求
     */
    HTTP("http"),

    /**
     * https请求
     */
    HTTPS("https");

    private String keyName;

    HttpProtocolType(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
}
