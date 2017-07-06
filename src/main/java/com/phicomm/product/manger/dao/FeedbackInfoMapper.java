package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.table.FeedbackInfoWithBLOBs;
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
}