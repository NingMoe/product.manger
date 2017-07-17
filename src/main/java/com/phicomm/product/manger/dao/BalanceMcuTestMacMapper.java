package com.phicomm.product.manger.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * mac
 * Created by wei.yang on 2017/7/17.
 */
@Repository
public interface BalanceMcuTestMacMapper {

    /**
     * 批量插入测试mac
     *
     * @param macList mac列表
     * @return 影响行数
     */
    int insertBatch(@Param("macList") List macList);

    /**
     * 清除mac
     */
    void cleanMac();
}
