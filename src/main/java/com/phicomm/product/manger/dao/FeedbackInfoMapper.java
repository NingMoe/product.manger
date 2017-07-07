package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.table.FeedbackInfoWithBLOBs;
import com.phicomm.product.manger.model.table.FeedbackRequestBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}