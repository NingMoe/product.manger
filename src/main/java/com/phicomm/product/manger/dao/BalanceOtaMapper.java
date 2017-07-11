package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.ota.BalanceOtaStatus;
import com.phicomm.product.manger.model.ota.BalanceOtaInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Ota
 * Created by wei.yang on 2017/7/10.
 */
@Repository
public interface BalanceOtaMapper {

    /**
     * 上传ota版本信息：返回插入的条数
     *
     * @param balanceOtaInfo 版本信息
     */
    int uploadOtaMessage(@Param("balanceOtaInfo") BalanceOtaInfo balanceOtaInfo);

    /**
     * 更新版本状态
     *
     * @param balanceOtaStatus 版本状态
     * @return 影响行数
     */
    int updateOtaStatus(@Param("balanceOtaStatus") BalanceOtaStatus balanceOtaStatus);

    /**
     * 清理其他版本状态
     *
     * @param balanceOtaStatus 版本信息
     */
    void cleanOtaStatus(@Param("balanceOtaStatus") BalanceOtaStatus balanceOtaStatus);

    /**
     * 获取版本列表
     *
     * @return 版本列表信息
     */
    List<BalanceOtaInfo> fetchOtaList();
}
