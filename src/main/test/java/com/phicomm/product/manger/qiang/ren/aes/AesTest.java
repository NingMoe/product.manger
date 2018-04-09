package com.phicomm.product.manger.qiang.ren.aes;

import com.phicomm.product.manger.service.GroupService;
import com.phicomm.product.manger.utils.AesUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * aes 测试
 * Created by qiang.ren on 2018/3/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/spring-root.xml"})
public class AesTest {


    @Test
    public void encryptTest(){
        System.out.println(AesUtil.encrypt("012342222244444444444445555555555666666666").length());
    }

    @Test
    public void decrpytTest(){
        System.out.println(AesUtil.decrpyt("QtPd1zd1KBo//a9SE0NGlg=="));
    }

    @Test
    public void test(){
        Long l = 12345654321L;
        System.out.println(l.toString());
    }
}
