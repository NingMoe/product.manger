package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.ShareStatisticMapper;
import com.phicomm.product.manger.model.statistic.ShareStatisticModel;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 分享数据service
 *
 * @author song02.cao
 */
@Service
public class ShareStatisticService {

    private ShareStatisticMapper shareStatisticMapper;

    private static final int DAYS = 14;

    @Autowired
    public ShareStatisticService(ShareStatisticMapper shareStatisticMapper) {
        this.shareStatisticMapper = shareStatisticMapper;
        Assert.notNull(this.shareStatisticMapper, "shareStatisticMapper is null");
    }

    /**
     * 获取14天内数据
     *
     * @return 返回的数据
     */
    public Map<String, List<Long>> getShareStatistic14Days() {
        LocalDate today = new LocalDate();
        LocalDate before14Day = today.minusDays(DAYS);
        List<ShareStatisticModel> list = shareStatisticMapper.
                getShareStatistic(before14Day.toDate().getTime(), today.toDate().getTime());

        System.out.println(list);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        Map<String, List<Long>> map = new HashMap<>(16);

        for (ShareStatisticModel shareStatisticModel : list) {
            String shareType = shareStatisticModel.getShareType();
            long shareCount = shareStatisticModel.getShareCount();
            LocalDate date = LocalDate.parse(shareStatisticModel.getShareDate(), dateTimeFormatter);
            int index = new Period(before14Day, date, PeriodType.days()).getDays();

            if (!map.keySet().contains(shareType)) {
                List<Long> shareCountList = new ArrayList<>(DAYS);
                for (int i = 0; i < DAYS; i++) {
                    shareCountList.add(i, 0L);
                }
                shareCountList.set(index, shareCount);
                map.put(shareType, shareCountList);
            } else {
                List shareCountList = map.get(shareType);
                shareCountList.set(index, shareCount);
            }
        }
        return map;
    }
}
