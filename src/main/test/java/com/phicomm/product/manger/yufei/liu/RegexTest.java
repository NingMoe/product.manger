package com.phicomm.product.manger.yufei.liu;

import com.phicomm.product.manger.utils.RegexUtil;
import org.junit.Test;

/**
 * 正则表达式测试
 * Created by yufei.liu on 2017/5/31.
 */
public class RegexTest {

    @Test
    public void test1() {
        System.out.println(RegexUtil.checkPhoneNumber(null));
    }

    @Test
    public void test2() {
        System.out.println(RegexUtil.checkPhoneNumber("100"));
    }

    @Test
    public void test3() {
        System.out.println(RegexUtil.checkPhoneNumber("13311921091"));
    }

    @Test
    public void test4() {
        System.out.println(RegexUtil.checkPhoneNumber("17193401323"));
    }

    @Test
    public void test5() {
        System.out.println("$baseUrl/a/b/c/d".replace("$baseUrl", "http://192.168.40.41:8080"));
    }
}
