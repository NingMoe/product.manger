package com.phicomm.product.manger.yufei.liu.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 处理鹿得医疗的数据
 * <p>
 * Created by yufei.liu on 2017/7/12.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring-root.xml"})
public class LudeTest {

    private static final String TEMPLATE = "<html><head><meta charset=\"utf-8\"></head><body><h2>TITLE</h2><br/><br/>SUMMARY<br/><br/>CONTENT<br/><br/><img src=\"PICTURE\"</img></body></html>";

    @Test
    public void test() {

    }

}
