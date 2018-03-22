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
     * @param macList      mac列表
     * @param firmwareType 固件类型
     * @param production   产品型号
     */
    void insertBatch(@Param("macList") List macList,
                    @Param("production") String production,
                    @Param("firmwareType") String firmwareType);

    /**
     * 清除mac
     *
     * @param firmwareType 固件类型
     * @param production   产品型号
     */
    void cleanMac(@Param("production") String production,
                  @Param("firmwareType") String firmwareType);
}
