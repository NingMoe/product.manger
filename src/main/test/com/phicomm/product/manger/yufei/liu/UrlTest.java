package com.phicomm.product.manger.yufei.liu;

import com.phicomm.product.manger.utils.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

/**
 * url单元测试
 *
 * @author yufei.liu
 */
public class UrlTest {

    @Test
    public void test() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("刘宇飞", "utf-8"));
        System.out.println(URLEncoder.encode("刘宇飞", "GBK"));
        System.out.println(URLEncoder.encode("刘宇飞", "ISO-8859-1"));
        System.out.println(URLEncoder.encode("刘宇飞", "gb2312"));
        System.out.println(new Date());
    }




}
