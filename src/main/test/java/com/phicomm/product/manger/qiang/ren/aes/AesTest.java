package com.phicomm.product.manger.qiang.ren.aes;

import com.phicomm.product.manger.utils.AesUtil;
import org.junit.Test;

/**
 * aes 测试
 * Created by qiang.ren on 2018/3/19.
 */
public class AesTest {

    @Test
    public void encryptTest(){
        System.out.println(AesUtil.encrypt("88888"));
    }

    @Test
    public void decrpytTest(){
        System.out.println(AesUtil.decrpyt("QtPd1zd1KBo//a9SE0NGlg=="));
    }

    @Test
    public void test(){
        System.out.println(123456);
    }
}
