package com.phicomm.product.manger.utils;

import com.google.common.base.Strings;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * Created by yufei.liu on 2017/5/31.
 */
public class RegexUtil {

    private final static String PHONE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";

    private final static String MAC_REGEX_SPLIT = "^([0-9a-fA-F]{2}:){5}([0-9a-fA-F]{2})$";

    private final static String MAC_REGEX = "^[0-9a-fA-F]{12}$";

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

    /**
     * 校验mac地址的格式
     *
     * @param mac mac地址
     * @return 格式是否正确
     */
    public static boolean checkMacFormat(String mac) {
        if (Strings.isNullOrEmpty(mac)) {
            return false;
        } else {
            int len = mac.getBytes().length;
            if (len == 12) {
                return Pattern.matches(MAC_REGEX, mac);
            } else if (len == 17) {
                return Pattern.matches(MAC_REGEX_SPLIT, mac);
            }
            return false;
        }

    }
}
