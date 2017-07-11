package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.firmware.FirmwareInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmwareInfoMapper {

    /**
     * 查看数据库是否存在相对应的版本以及版本号
     *
     * @param version     版本
     * @param versionCode 版本号
     */
    boolean exist(@Param("version") String version,
                  @Param("versionCode") int versionCode,
                  @Param("environment") String environment);

    /**
     * 向数据库中插入数据
     *
     * @param firmwareInfo 记录
     * @return 影响的行数
     */
    int insert(@Param("firmwareInfo") FirmwareInfo firmwareInfo);

}