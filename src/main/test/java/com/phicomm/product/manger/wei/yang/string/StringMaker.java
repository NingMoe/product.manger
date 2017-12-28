package com.phicomm.product.manger.wei.yang.string;

/**
 * string
 * Created by wei.yang on 2017/10/11.
 */
class StringMaker {

    private static final String CHAR_REPOSITORY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 获取固定长度的字符串
     *
     * @param len 长度
     * @return 字符串
     */
    static String maker(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(CHAR_REPOSITORY.charAt((int) Math.round(Math.random() * (CHAR_REPOSITORY.length() - 1))));
        }
        return builder.toString();
    }
}
