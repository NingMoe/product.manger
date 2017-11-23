package com.phicomm.product.manger.caosong.balance.statistic.service;

/**
 * Created by song02.cao on 2017/11/21.
 */
import com.phicomm.product.manger.service.BalanceActiveStatisticService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring-root.xml"})
public class BalanceActiveStatisticServiceTest {
    @Autowired
    BalanceActiveStatisticService balanceActiveStatisticService;

    @Test
    public void test() {
        balanceActiveStatisticService.getDrawChartData();
    }
}
