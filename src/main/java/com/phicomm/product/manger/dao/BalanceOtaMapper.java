package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.ota.BalanceOtaInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}
