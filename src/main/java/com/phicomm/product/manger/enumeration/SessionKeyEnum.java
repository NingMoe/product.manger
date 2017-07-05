package com.phicomm.product.manger.enumeration;

/**
 * session中的key
 * Created by yufei.liu on 2017/5/31.
 */
public enum SessionKeyEnum {

    /**
     * 登陆的标记
     */
    LOGIN_FLAG("FEEDBACK_LOGIN_FLAG"),

    /**
     * swagger登陆标记
     */
    SWAGGER_LOGIN_FLAG("SWAGGER_LOGIN_OK"),

    /**
     * feedback自己维护的用户信息
     */
    USER_INFO("FEEDBACK_USER_INFO"),

    /**
     * 斐讯统一账号维护的用户信息
     */
    USER_INFO_ACCOUNT("PHICOMM_USER_INFO"),

    /**
     * 授权列表
     */
    USER_PERMISSIONS("FEEDBACK_PERMISSIONS");

    private String keyName;

    SessionKeyEnum(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
}
