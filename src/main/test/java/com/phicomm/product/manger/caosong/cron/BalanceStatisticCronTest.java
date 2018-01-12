package com.phicomm.product.manger.caosong.cron;

import com.phicomm.product.manger.cron.BalanceStatisticCron;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * BalanceAccountInfo 测试
 *
 * @author song02.cao
 */
@WebAppConfiguration
@ContextConfiguration({"/spring/spring-root.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BalanceStatisticCronTest {

    @Autowired
    BalanceStatisticCron balanceStatisticCron;

    @Test
    public void AcountCountTest() {
        balanceStatisticCron.accountStatistic();
    }

    @Test
    public void userGenderCountTest() {
        balanceStatisticCron.userGenderCountStatistic();
    }

    @Test
    public void memberGenderCountTest() {
        balanceStatisticCron.memberGenderCountStatistic();
    }
}
