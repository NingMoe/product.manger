package com.phicomm.product.manger.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 测试MAC
 * Created by wei.yang on 2017/7/13.
 */
@Controller
public interface BalanceOtaTestMacMapper {

    /**
     * 批量插入测试mac
     *
     * @param macList mac列表
     * @return 影响行数
     */
    int insertBatch(@Param("macList") List macList);
}
