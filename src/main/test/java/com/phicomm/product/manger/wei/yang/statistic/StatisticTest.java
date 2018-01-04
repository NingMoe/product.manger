package com.phicomm.product.manger.wei.yang.statistic;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.product.manger.dao.TerminalStatisticMapper;
import com.phicomm.product.manger.model.terminal.SearchWithCertainTimeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author wei.yang on 2017/12/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/spring-root.xml"})
public class StatisticTest {

    @Autowired
    private TerminalStatisticMapper terminalStatisticMapper;

    @Test
    public void test() {
        SearchWithCertainTimeEntity certainTimeEntity = new SearchWithCertainTimeEntity();
        certainTimeEntity.setDateType("app_version");
        certainTimeEntity.setPlatform("android");
        certainTimeEntity.setSearchTime("2017-12-27");
        System.out.println(JSONObject.toJSONString(terminalStatisticMapper.obtainCompareData(certainTimeEntity)));
    }
}
