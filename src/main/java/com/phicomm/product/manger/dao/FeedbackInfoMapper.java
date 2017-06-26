package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.table.FeedbackInfoWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackInfoMapper {

    /**
     * 获取最后一条的Id
     *
     * @return id
     */
    long getLatestItemId();

    /**
     * 批量插入
     *
     * @param infoWithBLOBsList 反馈信息
     */
    void insertBatch(@Param("infoWithBLOBsList") List<FeedbackInfoWithBLOBs> infoWithBLOBsList);
}