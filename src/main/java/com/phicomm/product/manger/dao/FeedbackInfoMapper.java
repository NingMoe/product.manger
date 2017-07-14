package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.table.FeedbackInfoWithBLOBs;
import com.phicomm.product.manger.model.table.FeedbackRequestBean;
import com.phicomm.product.manger.model.table.FeedbackReview;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface FeedbackInfoMapper {

    /**
     * 获取pageSize条数据
     *
     * @param startId  获取的起点（包含）
     * @param pageSize 条数
     * @return 反馈意见
     */
    List<FeedbackInfoWithBLOBs> fetchFeedback(@Param("pageSize") int pageSize,
                                              @Param("startId") int startId);

    /**
     * 根据条件获取反馈意见
     *
     * @param requestBean 获取反馈意见的条件
     * @return 反馈意见
     */
    List<FeedbackInfoWithBLOBs> fetchFeedbackV2(@Param("requestBean") FeedbackRequestBean requestBean);

    /**
     * 创建反馈回复
     *
     * @param feedbackReview 反馈回复
     */
    void createFeedbackReview(@Param("feedbackReview") FeedbackReview feedbackReview);

    /**
     * 获取回复列表
     *
     * @param feedbackId 用户反馈ID
     * @return 反馈信息
     */
    List<FeedbackReview> listFeedbackReview(@Param("feedbackId") long feedbackId);

    /**
     * 删除回复
     *
     * @param feedbackReviewId 回复ID
     */
    void deleteFeedbackReview(@Param("feedbackReviewId") long feedbackReviewId);

    /**
     * 收藏用户反馈
     *
     * @param feedbackId 用户反馈ID
     */
    void collectFeedback(@Param("feedbackId") long feedbackId);

    /**
     * 取消收藏用户反馈
     *
     * @param feedbackId 用户反馈ID
     */
    void unCollectFeedback(@Param("feedbackId") long feedbackId);
}