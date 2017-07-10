package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.firmware.FirmwareInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmwareInfoMapper {

    /**
     * 向数据库中插入数据
     *
     * @param firmwareInfo 记录
     * @return 影响的行数
     */
    int insert(@Param("firmwareInfo") FirmwareInfo firmwareInfo);

}