package com.phicomm.product.manger.caosong.balance.statistic.service;

import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import com.phicomm.product.manger.service.BalanceDailyStatisticService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by song02.cao on 2017/11/17.
 */


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring-root.xml"})
public class BalanceDailyStatisticServiceTest {
    @Autowired
    BalanceDailyStatisticService balanceDailyStatisticService;

    @Test
    public void test() {
        CustomerContextHolder.selectTestDataSource();
        balanceDailyStatisticService.setBalanceStatisticHourUnit();
    }
}
