package com.phicomm.product.manger.service;

import com.google.common.base.Strings;
import com.phicomm.product.manger.dao.FeedbackInfoMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.table.FeedbackInfoWithBLOBs;
import com.phicomm.product.manger.model.table.FeedbackRequestBean;
import com.phicomm.product.manger.model.table.FeedbackReview;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Date;
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
     * 根据请求条件来获取反馈意见
     *
     * @param requestBean 请求条件
     * @return 反馈意见
     * @throws DataFormatException 数据格式异常
     */
    public List<FeedbackInfoWithBLOBs> fetchFeedbackV2(FeedbackRequestBean requestBean) throws DataFormatException {
        requestBean = checkParamFormat(requestBean);
        CustomerContextHolder.selectProdDataSource();
        List<FeedbackInfoWithBLOBs> feedbackInfoWithBLOBs = feedbackInfoMapper.fetchFeedbackV2(requestBean);
        CustomerContextHolder.clearDataSource();
        return formatFeedback(feedbackInfoWithBLOBs);
    }

    /**
     * 核对请求参数的格式
     * 只管两种情况：除过startId和pageSize全为空或全不为空，其余都当做格式错误
     *
     * @param requestBean 请求参数
     * @return 类型
     */
    private FeedbackRequestBean checkParamFormat(FeedbackRequestBean requestBean) throws DataFormatException {
        int pageSize = requestBean.getPageSize();
        String appType = requestBean.getAppType();
        Date startTime = requestBean.getStartTime();
        Date endTime = requestBean.getEndTime();
        if (pageSize <= 0) {
            requestBean.setPageSize(DEFAULT_PAGE_SIZE);
        }
        //全为空，设置默认值
        if (Strings.isNullOrEmpty(appType) && startTime == null && endTime == null) {
            requestBean.setAppType("balance");
            requestBean.setStartTime(new Date(0));
            requestBean.setEndTime(new DateTime().toDate());
        } else if (!Strings.isNullOrEmpty(appType) && startTime != null && endTime != null) {
            //开始大于结束
            if (startTime.getTime() > endTime.getTime()) {
                throw new DataFormatException();
            }
            //两者相同，则endTime加一天
            if (startTime.getTime() == endTime.getTime()) {
                requestBean.setEndTime(new DateTime(endTime).plusDays(1).toDate());
            }
        } else {
            throw new DataFormatException();
        }
        return requestBean;
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
            if (path.contains("<null>")){
                continue;
            }
            if (path.startsWith("group1")) {
                array[count.incrementAndGet()] = URL_HEADER + path;
            } else if (path.startsWith("http")) {
                array[count.incrementAndGet()] = anArray;
            }
        }
        return Arrays.copyOfRange(array, 0, count.get() + 1);
    }

    /**
     * 创建反馈回复
     *
     * @param feedbackReview 反馈回复
     */
    public void createFeedbackReview(FeedbackReview feedbackReview) {
        CustomerContextHolder.selectTestDataSource();
        feedbackInfoMapper.createFeedbackReview(feedbackReview);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 获取回复列表
     *
     * @param feedbackId 用户反馈ID
     * @return 反馈信息
     */
    public List<FeedbackReview> listFeedbackReview(long feedbackId) {
        CustomerContextHolder.selectTestDataSource();
        List<FeedbackReview> feedbackReview = feedbackInfoMapper.listFeedbackReview(feedbackId);
        CustomerContextHolder.clearDataSource();
        return feedbackReview;
    }

    /**
     * 删除回复
     *
     * @param feedbackReviewId 回复ID
     */
    public void deleteFeedbackReview(long feedbackReviewId) {
        CustomerContextHolder.selectTestDataSource();
        feedbackInfoMapper.deleteFeedbackReview(feedbackReviewId);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 收藏用户反馈
     *
     * @param feedbackId 用户反馈ID
     */
    public void collectFeedback(long feedbackId) {
        CustomerContextHolder.selectTestDataSource();
        feedbackInfoMapper.collectFeedback(feedbackId);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 取消收藏用户反馈
     *
     * @param feedbackId 用户反馈ID
     */
    public void unCollectFeedback(long feedbackId) {
        CustomerContextHolder.selectTestDataSource();
        feedbackInfoMapper.unCollectFeedback(feedbackId);
        CustomerContextHolder.clearDataSource();
    }
}
