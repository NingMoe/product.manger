package com.phicomm.product.manger.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * Created by yufei.liu on 2017/5/31.
 */
public class RegexUtil {

    private final static String PHONE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";

    /**
     * 构造函数私有
     */
    private RegexUtil() {

    }

    /**
     * 校验手机号的正则表达式
     *
     * @param phoneNumber 售假货
     * @return 如果通过则返回true
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber != null && Pattern.matches(PHONE_REGEX, phoneNumber);
    }

}
