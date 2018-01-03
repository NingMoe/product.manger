package com.phicomm.product.manger.caosong.service;

import com.phicomm.product.manger.service.ShareStatisticService;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

/**
 * ShareStatisticService 测试
 *
 * @author song02.cao
 */
@WebAppConfiguration
@ContextConfiguration({"/spring/spring-root.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ShareStatisticServiceTest {

    @Autowired
    ShareStatisticService shareStatisticService;

    @Test
    public void test() {
        LocalDate today = new LocalDate();
        LocalDate before20Days = today.minusDays(20);
        LocalDate before14Days = today.minusDays(14);
        LocalDate before10Days = today.minusDays(10);
        LocalDate before5Days = today.minusDays(5);

        System.out.println("20 ago: " + before20Days.toDate().getTime());
        System.out.println("14 ago: " + before14Days.toDate().getTime());
        System.out.println("10 ago: " + before10Days.toDate().getTime());
        System.out.println("5 ago: " + before5Days.toDate().getTime());
        Map<String, List<Long>> map = shareStatisticService.getShareStatistic14Days();
        System.out.println(map);
    }
}
