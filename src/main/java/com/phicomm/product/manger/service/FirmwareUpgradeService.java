package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.phicomm.product.manger.dao.FirmwareInfoMapper;
import com.phicomm.product.manger.dao.FirmwareTriggerParamConfigMapper;
import com.phicomm.product.manger.enumeration.FirmwareEnvironmentEnum;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.UploadFileException;
import com.phicomm.product.manger.exception.VersionHasExistException;
import com.phicomm.product.manger.exception.VersionNotExistException;
import com.phicomm.product.manger.model.firmware.FirmwareInfo;
import com.phicomm.product.manger.module.fota.DefaultFirmwareUpgradeTrigger;
import com.phicomm.product.manger.module.fota.FirmwareUpgradeContext;
import com.phicomm.product.manger.utils.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 固件升级处理逻辑
 * <p>
 * Created by yufei.liu on 2017/7/10.
 */
@Service
public class FirmwareUpgradeService {

    private static final Logger logger = Logger.getLogger(FirmwareUpgradeService.class);

    private FirmwareInfoMapper firmwareInfoMapper;

    private FirmwareTriggerParamConfigMapper firmwareTriggerParamConfigMapper;

    @Autowired
    public FirmwareUpgradeService(FirmwareInfoMapper firmwareInfoMapper,
                                  FirmwareTriggerParamConfigMapper firmwareTriggerParamConfigMapper) {
        this.firmwareInfoMapper = firmwareInfoMapper;
        this.firmwareTriggerParamConfigMapper = firmwareTriggerParamConfigMapper;
        Assert.notNull(this.firmwareInfoMapper);
        Assert.notNull(this.firmwareTriggerParamConfigMapper);
    }

    /**
     * 文件上传
     */
    public void firmwareUpgradeWristbandFileUpload(String firmwareType,
                                                   String hardwareVersion,
                                                   String firmwareVersion,
                                                   String environment,
                                                   MultipartFile file,
                                                   String description)
            throws DataFormatException, UploadFileException, VersionHasExistException {
        // 校验参数
        checkFirmwareUpgradeWristbandFileUpload(firmwareType, hardwareVersion, firmwareVersion, environment, file, description);
        int firmwareVersionCode = Integer.parseInt(firmwareVersion);
        if (firmwareInfoMapper.exist(firmwareType, hardwareVersion, environment, firmwareVersion, firmwareVersionCode)) {
            throw new VersionHasExistException();
        }
        // 上传文件
        String downloadUrl = FileUtil.uploadFileToHermes(file);
        FirmwareInfo firmwareInfo = new FirmwareInfo();
        firmwareInfo.setFirmwareType(firmwareType);
        firmwareInfo.setHardwareCode(hardwareVersion);
        firmwareInfo.setVersion(file.getOriginalFilename());
        firmwareInfo.setVersionCode(Integer.parseInt(firmwareVersion));
        firmwareInfo.setEnvironment(environment);
        firmwareInfo.setEnable(0);
        firmwareInfo.setUrl(downloadUrl);
        firmwareInfo.setDescription(Strings.nullToEmpty(description).trim());
        logger.info(firmwareInfo);
        firmwareInfoMapper.insert(firmwareInfo);
    }

    /**
     * 校验参数
     *
     * @param firmwareType    固件类型
     * @param hardwareVersion 硬件版本
     * @param firmwareVersion 固件版本号
     * @param environment     环境
     * @param file            上传的文件
     */
    private void checkFirmwareUpgradeWristbandFileUpload(String firmwareType,
                                                         String hardwareVersion,
                                                         String firmwareVersion,
                                                         String environment,
                                                         MultipartFile file,
                                                         String description) throws DataFormatException {
        logger.info(String.format("firmwareType = %s", firmwareType));
        logger.info(String.format("hardwareVersion = %s", hardwareVersion));
        logger.info(String.format("firmwareVersion = %s", firmwareVersion));
        logger.info(String.format("environment = %s", environment));
        logger.info(String.format("description = %s", description));
        logger.info(String.format("file = %s", file != null ? file.getOriginalFilename() : null));
        if (Strings.isNullOrEmpty(firmwareType)
                || Strings.isNullOrEmpty(hardwareVersion)
                || Strings.isNullOrEmpty(firmwareVersion)
                || Strings.isNullOrEmpty(environment)
                || file == null
                || file.isEmpty()) {
            throw new DataFormatException();
        }
        try {
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(firmwareVersion);
        } catch (Exception e) {
            throw new DataFormatException();
        }
        if (!FirmwareEnvironmentEnum.TEST.getEnvironment().equals(environment) &&
                !FirmwareEnvironmentEnum.PROD.getEnvironment().equals(environment)) {
            throw new DataFormatException();
        }
    }

    /**
     * 获取test、prod下面的数据（最多展示100条）
     *
     * @param environment 环境
     */
    public JSONObject firmwareUpgradeWristbandList(String environment) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        result.put("data", jsonArray);
        List<FirmwareInfo> firmwareInfoList = firmwareInfoMapper.getFirmwareInfoList(environment);
        if (firmwareInfoList != null) {
            firmwareInfoList.forEach((item) -> {
                Map<String, String> map = Maps.newHashMap();
                map.put("firmwareType", item.getFirmwareType());
                map.put("hardwareCode", item.getHardwareCode());
                map.put("version", item.getVersion());
                map.put("versionCode", String.valueOf(item.getVersionCode()));
                map.put("enable", item.getEnable() == 0 ? "" : "<i class=\"fa fa-star\"></i>");
                jsonArray.add(map);
            });
        }
        return result;
    }

    /**
     * 获取固件详情
     *
     * @param firmwareType 固件类型
     * @param hardwareCode 硬件版本号
     * @param versionCode  固件版本号
     */
    public FirmwareInfo getFirmwareDetail(String firmwareType,
                                          String hardwareCode,
                                          String environment,
                                          String versionCode) {
        FirmwareInfo firmwareInfo = firmwareInfoMapper.getFirmwareDetail(firmwareType,
                hardwareCode, environment, Integer.parseInt(versionCode));
        if (firmwareInfo == null) {
            return new FirmwareInfo();
        }
        return firmwareInfo;
    }

    /**
     * 修改当前最新的版本
     *
     * @param firmwareType 固件类型
     * @param hardwareCode 硬件版本号
     * @param versionCode  固件版本号
     * @param environment  环境
     */
    @Transactional(rollbackFor = Throwable.class)
    public void modifyCurrentFirmwareVersion(String firmwareType,
                                             String hardwareCode,
                                             String environment,
                                             String versionCode) throws VersionNotExistException {
        boolean exist = firmwareInfoMapper.exist(firmwareType, hardwareCode, environment, "", Integer.parseInt(versionCode));
        if(!exist) {
            throw new VersionNotExistException();
        }
        firmwareInfoMapper.cleanFirmware(firmwareType, hardwareCode, environment);
        int affectRows = firmwareInfoMapper.enableFirmware(firmwareType, hardwareCode, environment, Integer.parseInt(versionCode));
        if(affectRows < 1) {
            throw new VersionNotExistException();
        }
        // 通知其他项目版本变更
        trigger(firmwareType, hardwareCode, environment, Integer.parseInt(versionCode));
    }

    /**
     * 触发升级
     */
    private void trigger(String firmwareType,
                         String hardwareCode,
                         String environment,
                         int versionCode) {
        FirmwareEnvironmentEnum firmwareEnvironmentEnum = "test".equals(environment) ?
                FirmwareEnvironmentEnum.TEST : FirmwareEnvironmentEnum.PROD;
        FirmwareInfo firmwareInfo = firmwareInfoMapper.getFirmwareDetail(firmwareType,
                hardwareCode, environment, versionCode);
        String param = firmwareTriggerParamConfigMapper.getFirmwareConfig();;
        FirmwareUpgradeContext firmwareUpgradeContext = new FirmwareUpgradeContext(firmwareType,
                hardwareCode, firmwareEnvironmentEnum, versionCode, firmwareInfo, param);
        new Thread(() -> new DefaultFirmwareUpgradeTrigger().trigger(firmwareUpgradeContext)).start();
    }

    /**
     * 获取配置信息
     *
     * @return 获取配置信息
     */
    public String getFirmwareConfig() {
        String configuation = firmwareTriggerParamConfigMapper.getFirmwareConfig();
        return Strings.nullToEmpty(configuation);
    }

    /**
     * 设置配置信息
     */
    @Transactional(rollbackFor = Throwable.class)
    public void setFirmwareConfig(String configuation) {
        firmwareTriggerParamConfigMapper.clean();
        firmwareTriggerParamConfigMapper.insert(configuation);
    }
}
