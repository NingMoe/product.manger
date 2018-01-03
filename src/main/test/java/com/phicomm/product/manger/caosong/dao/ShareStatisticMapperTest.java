package com.phicomm.product.manger.caosong.dao;

import com.phicomm.product.manger.dao.ShareStatisticMapper;
import com.phicomm.product.manger.model.statistic.ShareStatisticModel;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * shareStatisticMapperTest
 *
 * @author song02.cao
 */
@WebAppConfiguration
@ContextConfiguration({"/spring/spring-root.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ShareStatisticMapperTest {

    @Autowired
    ShareStatisticMapper shareStatisticMapper;

    @Test
    public void test() {
        LocalDate today = new LocalDate();
        System.out.println(today.toDate().getTime());
        LocalDate beforeDate = today.minusDays(100);
        System.out.println(beforeDate.toDate().getTime());
        List<ShareStatisticModel> list = shareStatisticMapper.getShareStatistic(beforeDate.toDate().getTime(), today.toDate().getTime());
        System.out.println(list);
    }

}
