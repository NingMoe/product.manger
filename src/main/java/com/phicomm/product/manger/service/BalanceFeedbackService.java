package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.FeedbackInfoMapper;
import com.phicomm.product.manger.model.table.FeedbackInfoWithBLOBs;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 接收用户反馈信息
 * Created by yufei.liu on 2017/2/22.
 */
@Service
public class BalanceFeedbackService {

    private static final String URL_HEADER = "https://ihome.phicomm.com:2580/hermes/image/";

    private FeedbackInfoMapper feedbackInfoMapper;

    private static final int DEFAULT_PAGE_SIZE = 20;

    @Autowired
    public BalanceFeedbackService(FeedbackInfoMapper feedbackInfoMapper) {
        this.feedbackInfoMapper = feedbackInfoMapper;
        Assert.notNull(this.feedbackInfoMapper);
    }

    /**
     * 获取反馈列表
     *
     * @param pageSize 条数
     * @param startId  起点
     * @return 反馈信息
     */
    public List<FeedbackInfoWithBLOBs> fetchFeedback(int pageSize, int startId) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        CustomerContextHolder.selectProdDataSource();
        List<FeedbackInfoWithBLOBs> feedbackInfoWithBLOBs = feedbackInfoMapper.fetchFeedback(pageSize, startId);
        CustomerContextHolder.clearDataSource();
        return formatFeedback(feedbackInfoWithBLOBs);
    }

    /**
     * 格式化图像url:由于网络问题或老版本问题，导致url不全或为null
     *
     * @param feedbackInfoWithBLOBs 反馈信息列表
     * @return 格式化好的信息
     */
    private List<FeedbackInfoWithBLOBs> formatFeedback(List<FeedbackInfoWithBLOBs> feedbackInfoWithBLOBs) {
        if (feedbackInfoWithBLOBs.isEmpty()) {
            return feedbackInfoWithBLOBs;
        }
        for (FeedbackInfoWithBLOBs infoWithBLOBs : feedbackInfoWithBLOBs) {
            infoWithBLOBs.setImageUrl(toArray(infoWithBLOBs.getPicture()));
        }
        return feedbackInfoWithBLOBs;
    }

    /**
     * 将由Array.toString（）转化的字符串转化为原来的数组
     *
     * @param data 字符串
     * @return 数组
     */
    private static String[] toArray(String data) {
        if (data.length() < 10) {
            return null;
        }
        String[] array = data.split(",");
        for (int i = 0; i < array.length; i++) {
            if (array[i].startsWith("[")) {
                array[i] = array[i].substring(1);
            }
            if (array[i].endsWith("]")) {
                array[i] = array[i].substring(0, array[i].length() - 1);
            }
        }
        return dealUrlFormat(array);
    }

    /**
     * url格式恢复
     *
     * @param array url组
     * @return 修复的数组
     */
    private static String[] dealUrlFormat(String[] array) {
        AtomicInteger count = new AtomicInteger(-1);
        if (array.length == 1 && array[0].length() < 10) {
            return null;
        }
        for (String anArray : array) {
            String path = anArray.subSequence(1, anArray.length() - 1).toString();
            if (path.startsWith("group1")) {
                array[count.incrementAndGet()] = URL_HEADER + path;
            } else if (path.startsWith("http")) {
                array[count.incrementAndGet()] = anArray;
            }
        }
        return Arrays.copyOfRange(array, 0, count.get() + 1);
    }
}
