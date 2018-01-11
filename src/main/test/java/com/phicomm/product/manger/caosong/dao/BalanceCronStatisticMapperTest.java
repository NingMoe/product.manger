package com.phicomm.product.manger.caosong.dao;

import com.phicomm.product.manger.dao.BalanceCronStatisticMapper;
import com.phicomm.product.manger.model.statistic.BalanceAccountInfo;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * balanceCronStatisticMapper的测试
 *
 * @author song02.cao
 */
@WebAppConfiguration
@ContextConfiguration({"/spring/spring-root.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BalanceCronStatisticMapperTest {

    @Autowired
    BalanceCronStatisticMapper balanceCronStatisticMapper;

    @Test
    public void getTest() {
        CustomerContextHolder.selectLocalDataSource();
        BalanceAccountInfo balanceAccountInfo = balanceCronStatisticMapper.getBalanceAccountInfo();
        System.out.println(balanceAccountInfo);
        CustomerContextHolder.clearDataSource();
    }

    @Test
    public void replaceTest() {
        CustomerContextHolder.selectLocalDataSource();
        balanceCronStatisticMapper.insertUpateBalanceAccountInfo(1, 1, 1);
        CustomerContextHolder.clearDataSource();
    }
}
