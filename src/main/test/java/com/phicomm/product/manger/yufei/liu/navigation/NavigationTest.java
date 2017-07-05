package com.phicomm.product.manger.yufei.liu.navigation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 测试解析xml
 * Created by yufei.liu on 2017/6/5.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring-root.xml"})
public class NavigationTest {

    @Test
    public void test() {
        System.out.println("+++++++++++++");
    }

}
