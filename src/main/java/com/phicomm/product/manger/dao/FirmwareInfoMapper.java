package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.firmware.FirmwareInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirmwareInfoMapper {

    /**
     * 查看数据库是否存在相对应的版本以及版本号
     *
     * @param version     版本
     * @param versionCode 版本号
     */
    boolean exist(@Param("firmwareType") String firmwareType,
                  @Param("hardwareCode") String hardwareCode,
                  @Param("environment") String environment,
                  @Param("version") String version,
                  @Param("versionCode") int versionCode);

    /**
     * 查看数据库是否存在相对应的APP名字
     *
     * @param appName APP版本
     */
    boolean existApp(@Param("appName") String appName);

    /**
     * 向数据库中插入数据
     *
     * @param firmwareInfo 记录
     * @return 影响的行数
     */
    int insert(@Param("firmwareInfo") FirmwareInfo firmwareInfo);

    /**
     * 更新数据
     *
     * @param firmwareInfo 记录
     */
    void update(@Param("firmwareInfo") FirmwareInfo firmwareInfo);

    /**
     * 获得固件列表
     *
     * @param environment 环境
     * @return 固件信息
     */
    List<FirmwareInfo> getFirmwareInfoList(@Param("environment") String environment);

    /**
     * 获得APP名字列表
     *
     * @return APP名字列表
     */
    List<String> getAppNameList();

    /**
     * 获取固件详情
     *
     * @param firmwareType 固件类型
     * @param hardwareCode 硬件版本
     * @param versionCode  版本号
     * @param appName      APP名字
     */
    FirmwareInfo getFirmwareDetail(@Param("firmwareType") String firmwareType,
                                   @Param("hardwareCode") String hardwareCode,
                                   @Param("environment") String environment,
                                   @Param("versionCode") Integer versionCode,
                                   @Param("appName") String appName);

    /**
     * 清理固件
     *
     * @param firmwareType 固件类型
     * @param hardwareCode 硬件版本号
     * @param environment  环境
     */
    void cleanFirmware(@Param("firmwareType") String firmwareType,
                       @Param("hardwareCode") String hardwareCode,
                       @Param("environment") String environment);

    /**
     * 修改当前的固件版本
     *
     * @param firmwareType 固件类型
     * @param hardwareCode 硬件版本号
     * @param environment  环境
     * @param versionCode  固件版本号
     */
    int enableFirmware(@Param("firmwareType") String firmwareType,
                       @Param("hardwareCode") String hardwareCode,
                       @Param("environment") String environment,
                       @Param("versionCode") Integer versionCode);

    /**
     * 获得固件信息
     */
    FirmwareInfo getFirmwareInfo(@Param("id") Integer id);

    /**
     * 设置是否可用
     */
    void setEnable(@Param("id") Integer id,
                   @Param("enable") int enable);

    /**
     * 删除该固件
     */
    void delete(@Param("id") Integer id);
}