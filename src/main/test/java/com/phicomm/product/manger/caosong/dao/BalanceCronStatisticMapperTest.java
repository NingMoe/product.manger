package com.phicomm.product.manger.caosong.dao;

import com.phicomm.product.manger.dao.BalanceCronStatisticMapper;
import com.phicomm.product.manger.model.statistic.BalanceAccountInfo;
import com.phicomm.product.manger.model.statistic.LocationCountBean;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Test
    public void getUserCountTest() {
        CustomerContextHolder.selectLocalDataSource();
        Map<String, Map<String, Integer>> countMap = balanceCronStatisticMapper.getBalanceUserCountInfo();
        System.out.println("countMap = " + countMap);
    }

    @Test
    public void replaceUserCountTest() {
        CustomerContextHolder.selectLocalDataSource();
        balanceCronStatisticMapper.insertUpdateBalanceUserCountInfo(0, "男", 500);
        balanceCronStatisticMapper.insertUpdateBalanceUserCountInfo(1, "女", 400);
        CustomerContextHolder.clearDataSource();
    }

    @Test
    public void getBalanceSaleLocationCountTest() {
        CustomerContextHolder.selectLocalDataSource();
        List<LocationCountBean> map = balanceCronStatisticMapper.getBalanceSaleLocationCountInfo();
        System.out.println(map);
        CustomerContextHolder.clearDataSource();
    }

    @Test
    public void insertBalanceSaleLocationCountTest() {
        CustomerContextHolder.selectLocalDataSource();
        List<LocationCountBean> list = new ArrayList<>();
        LocationCountBean locationCountBean1 = new LocationCountBean();
        locationCountBean1.setProvince("广东");
        locationCountBean1.setGenerateCount(100);

        LocationCountBean locationCountBean2 = new LocationCountBean();
        locationCountBean2.setProvince("广西");
        locationCountBean2.setGenerateCount(100);

        LocationCountBean locationCountBean3 = new LocationCountBean();
        locationCountBean3.setProvince("河南");
        locationCountBean3.setGenerateCount(100);

        LocationCountBean locationCountBean4 = new LocationCountBean();
        locationCountBean4.setProvince("河北");
        locationCountBean4.setGenerateCount(100);

        list.add(locationCountBean1);
        list.add(locationCountBean2);
        list.add(locationCountBean3);
        list.add(locationCountBean4);
        balanceCronStatisticMapper.insertBalanceSaleLocationCountInfo(list);
    }

    @Test
    public void getSaleLocationCountInfoTest() {
        CustomerContextHolder.selectLocalDataSource();
        List<LocationCountBean> list = balanceCronStatisticMapper.getBalanceSaleLocationCountInfo();
        System.out.println(list);
    }

    @Test
    public void cleanSaleLocationCountInfoTest() {
        CustomerContextHolder.selectLocalDataSource();
        balanceCronStatisticMapper.cleanBalanceSaleLocationCountInfo();
        CustomerContextHolder.clearDataSource();
    }

    @Test
    public void insertBalanceSaleLocation12MonthsCountTest() {
        CustomerContextHolder.selectLocalDataSource();
        List<LocationCountBean> list = new ArrayList<>();
        LocationCountBean locationCountBean1 = new LocationCountBean();
        locationCountBean1.setProvince("广东");
        locationCountBean1.setGenerateCount(100);

        LocationCountBean locationCountBean2 = new LocationCountBean();
        locationCountBean2.setProvince("广西");
        locationCountBean2.setGenerateCount(100);

        LocationCountBean locationCountBean3 = new LocationCountBean();
        locationCountBean3.setProvince("河南");
        locationCountBean3.setGenerateCount(100);

        LocationCountBean locationCountBean4 = new LocationCountBean();
        locationCountBean4.setProvince("河北");
        locationCountBean4.setGenerateCount(100);

        list.add(locationCountBean1);
        list.add(locationCountBean2);
        list.add(locationCountBean3);
        list.add(locationCountBean4);
        balanceCronStatisticMapper.insertBalanceSaleLocation12MonthsCountInfo(list);
    }

    @Test
    public void getSaleLocation12MonthsCountInfoTest() {
        CustomerContextHolder.selectLocalDataSource();
        List<LocationCountBean> list = balanceCronStatisticMapper.getBalanceSaleLocation12MonthsCountInfo();
        System.out.println(list);
    }

    @Test
    public void cleanSaleLocation12MonthsCountInfoTest() {
        CustomerContextHolder.selectLocalDataSource();
        balanceCronStatisticMapper.cleanBalanceSaleLocation12MonthsCountInfo();
        CustomerContextHolder.clearDataSource();
    }


}
