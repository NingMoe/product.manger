package com.phicomm.product.manger.caosong.service;

import com.phicomm.product.manger.service.BalanceLocationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by song02.cao on 2018/1/12.
 */
@WebAppConfiguration
@ContextConfiguration({"/spring/spring-root.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BalanceLocationServiceTest {

    @Autowired
    BalanceLocationService balanceLocationService;

    @Test
    public void test() {
        balanceLocationService.obtainLocationCount30Days();
    }
}
