package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.mcu.BalanceMcuBean;
import com.phicomm.product.manger.model.mcu.BalanceMcuStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * mcu
 * Created by wei.yang on 2017/7/17.
 */
@Repository
public interface BalanceMcuMapper {

    /**
     * 获取mcu版本列表
     */
    List<BalanceMcuBean> fetchMcuList();

    /**
     * 上传mcu信息
     *
     * @param balanceMcuBean mcu版本信息
     * @return 影响行数
     */
    int uploadMcuMessage(@Param("balanceMcuBean") BalanceMcuBean balanceMcuBean);

    /**
     * 修改版本信息
     *
     * @param balanceMcuStatus mcu版本信息
     */
    void modifyMcuVersionStatus(@Param("balanceMcuStatus") BalanceMcuStatus balanceMcuStatus);

    /**
     * 清理其他的版本信息
     *
     * @param balanceMcuStatus 版本信息
     */
    void cleanMcuStatus(@Param("balanceMcuStatus") BalanceMcuStatus balanceMcuStatus);
}
