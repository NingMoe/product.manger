package com.phicomm.product.manger.yufei.liu.permission;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 解析xml文件
 * Created by yufei.liu on 2017/6/2.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring-root.xml"})
public class PermissionReadXmlTest {

    @Test
    public void test() {
        System.out.println("++++++++++++++");
    }

}
