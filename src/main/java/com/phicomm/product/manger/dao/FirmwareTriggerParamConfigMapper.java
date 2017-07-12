package com.phicomm.product.manger.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmwareTriggerParamConfigMapper {

    /**
     * 获取配置信息
     */
    String getFirmwareConfig();

    /**
     * 清除
     */
    void clean();

    /**
     * 插入
     */
    void insert(@Param("param") String param);

}