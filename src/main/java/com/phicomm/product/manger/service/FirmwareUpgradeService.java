package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.phicomm.product.manger.dao.FirmwareInfoMapper;
import com.phicomm.product.manger.dao.FirmwareTriggerParamConfigMapper;
import com.phicomm.product.manger.enumeration.FirmwareEnvironmentEnum;
import com.phicomm.product.manger.exception.*;
import com.phicomm.product.manger.model.firmware.FirmwareInfo;
import com.phicomm.product.manger.module.fota.DefaultFirmwareUpgradeTrigger;
import com.phicomm.product.manger.module.fota.FirmwareUpgradeContext;
import com.phicomm.product.manger.utils.ExceptionUtil;
import com.phicomm.product.manger.utils.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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
     * 选择App
     */
    public void whichPlatform(String firmwareType,
                              String hardwareVersion,
                              String firmwareVersion,
                              String environment,
                              String gnssVersion,
                              MultipartFile file,
                              String description,
                              String appPlatform,
                              String appVersionCodeAndroid,
                              String appVersionCodeIos) throws UploadFileException, VersionHasExistException, DataFormatException {
        if (!Strings.isNullOrEmpty(appPlatform)) {
            String[] appPlatforms = appPlatform.split(",");
            if (appPlatforms.length == 2) {
                firmwareUpgradeWristbandFileAdd(firmwareType, hardwareVersion, firmwareVersion, environment, gnssVersion, file, description, "android", appVersionCodeAndroid, appVersionCodeIos);
            } else if (appPlatforms.length == 1 && "android".equals(appPlatforms[0])) {
                firmwareUpgradeWristbandFileAdd(firmwareType, hardwareVersion, firmwareVersion, environment, gnssVersion, file, description, "android", appVersionCodeAndroid, null);
            } else if (appPlatforms.length == 1 && "ios".equals(appPlatforms[0])) {
                firmwareUpgradeWristbandFileAdd(firmwareType, hardwareVersion, firmwareVersion, environment, gnssVersion, file, description, "ios", appVersionCodeIos, null);
            }
        }

    }

    /**
     * 固件&APP上传处理逻辑
     */
    private void firmwareUpgradeWristbandFileAdd(String firmwareType,
                                                 String hardwareVersion,
                                                 String firmwareVersion,
                                                 String environment,
                                                 String gnssVersion,
                                                 MultipartFile file,
                                                 String description,
                                                 String appPlatform,
                                                 String appVersionCode,
                                                 String appVersionCodeIos)
            throws DataFormatException, UploadFileException, VersionHasExistException {
        // 校验参数
        checkFirmwareUpgradeWristbandFileAdd(firmwareType, hardwareVersion, firmwareVersion,
                environment, gnssVersion, file, description, appPlatform, appVersionCode);
        String[] appVersions = appVersionCode.trim().replaceAll(" ", "").replaceAll("，", ",").split(",");
        if (firmwareInfoMapper.exist(firmwareType, hardwareVersion, environment, firmwareVersion, appPlatform, appVersionCode)) {
            throw new VersionHasExistException();
        }
        // 上传文件
        Map<String, String> result = FileUtil.uploadFileToHermes(file);
        String downloadUrl = result.get("url");
        String md5 = result.get("md5");
        float size = file.getSize();
        FirmwareInfo firmwareInfo = new FirmwareInfo();
        firmwareInfo.setFirmwareType(firmwareType);
        firmwareInfo.setHardwareCode(hardwareVersion);
        firmwareInfo.setVersion(file.getOriginalFilename().replace(".zip", ""));
        firmwareInfo.setVersionCode(firmwareVersion);
        firmwareInfo.setEnvironment(environment);
        firmwareInfo.setGnssVersion(gnssVersion);
        firmwareInfo.setEnable(1);
        firmwareInfo.setUrl(downloadUrl);
        firmwareInfo.setMd5(md5);
        firmwareInfo.setDescription(Strings.nullToEmpty(description).trim());
        firmwareInfo.setAppPlatform(appPlatform);
        firmwareInfo.setSize(size);
        for (String appVersion : appVersions) {
            if (Strings.isNullOrEmpty(appVersion)){
                firmwareInfo.setAppVersionCode(appVersion);
                logger.info(firmwareInfo);
                firmwareInfoMapper.insert(firmwareInfo);
                // 触发升级
                trigger(firmwareType, hardwareVersion, environment, firmwareVersion, appPlatform, appVersionCode);
            }
        }
        if (!Strings.isNullOrEmpty(appVersionCodeIos)) {
            String[] appIosVersions = appVersionCodeIos.trim().replaceAll(" ", "").replaceAll("，", ",").split(",");
            for (String appIosVersion :appIosVersions) {
                if (Strings.isNullOrEmpty(appIosVersion)){
                    firmwareInfo.setAppPlatform("ios");
                    firmwareInfo.setAppVersionCode(appIosVersion);
                    logger.info(firmwareInfo);
                    firmwareInfoMapper.insert(firmwareInfo);
                    // 触发升级
                    trigger(firmwareType, hardwareVersion, environment, firmwareVersion, appPlatform, appVersionCode);
                }
            }

        }
    }

    /**
     * 固件编辑处理逻辑
     */
    public void firmwareUpgradeWristbandFileUpdate(String firmwareType,
                                                   String hardwareVersion,
                                                   String firmwareVersion,
                                                   String environment,
                                                   String gnssVersion,
                                                   MultipartFile file,
                                                   String description,
                                                   String appPlatform,
                                                   String appVersionCode,
                                                   String id,
                                                   String enableString)
            throws DataFormatException, UploadFileException, VersionHasExistException {
        // 校验参数
        checkFirmwareUpgradeWristbandFileAdd(firmwareType, hardwareVersion, firmwareVersion,
                environment, gnssVersion, file, description, appPlatform, appVersionCode);
        int enable = "可用".equals(enableString) ? 1 : 0;
        long longId = Long.parseLong(id);
        // 上传文件
        Map<String, String> result = FileUtil.uploadFileToHermes(file);
        String downloadUrl = result.get("url");
        String md5 = result.get("md5");
        float size = file.getSize();
        FirmwareInfo firmwareInfo = new FirmwareInfo();
        firmwareInfo.setId(longId);
        firmwareInfo.setEnable(enable);
        firmwareInfo.setFirmwareType(firmwareType);
        firmwareInfo.setHardwareCode(hardwareVersion);
        firmwareInfo.setVersion(file.getOriginalFilename().replace(".zip", ""));
        firmwareInfo.setVersionCode(firmwareVersion);
        firmwareInfo.setEnvironment(environment);
        firmwareInfo.setGnssVersion(gnssVersion);
        firmwareInfo.setUrl(downloadUrl);
        firmwareInfo.setMd5(md5);
        firmwareInfo.setDescription(Strings.nullToEmpty(description).trim());
        firmwareInfo.setAppPlatform(appPlatform);
        firmwareInfo.setAppVersionCode(appVersionCode);
        firmwareInfo.setSize(size);
        logger.info(firmwareInfo);
        if (firmwareInfoMapper.exist(firmwareType, hardwareVersion, environment, firmwareVersion, appPlatform, appVersionCode)) {
            throw new VersionHasExistException();
        }
        firmwareInfoMapper.update(firmwareInfo);
        // 触发升级
        trigger(firmwareType, hardwareVersion, environment, firmwareVersion, appPlatform, appVersionCode);
    }

    /**
     * 触发升级
     */
    private void trigger(String firmwareType,
                         String hardwareCode,
                         String environment,
                         String versionCode,
                         String appPlatform,
                         String appVersionCode) {
        FirmwareEnvironmentEnum firmwareEnvironmentEnum = "test".equals(environment) ?
                FirmwareEnvironmentEnum.TEST : FirmwareEnvironmentEnum.PROD;
        FirmwareInfo firmwareInfo = firmwareInfoMapper.getFirmwareDetail(firmwareType,
                hardwareCode, environment, versionCode, appPlatform, appVersionCode);
        String param = firmwareTriggerParamConfigMapper.getFirmwareConfig();
        FirmwareUpgradeContext firmwareUpgradeContext = new FirmwareUpgradeContext(firmwareType,
                hardwareCode, firmwareEnvironmentEnum, versionCode, firmwareInfo, param);
        new Thread(() -> {
            try {
                new DefaultFirmwareUpgradeTrigger().trigger(firmwareUpgradeContext);
            } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
                logger.info(ExceptionUtil.getErrorMessage(e));
            }
        }).start();
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
    private void checkFirmwareUpgradeWristbandFileAdd(String firmwareType,
                                                      String hardwareVersion,
                                                      String firmwareVersion,
                                                      String environment,
                                                      String gnssVersion,
                                                      MultipartFile file,
                                                      String description,
                                                      String appPlatform,
                                                      String appVersionCode) throws DataFormatException {
        logger.info(String.format("firmwareType = %s", firmwareType));
        logger.info(String.format("hardwareVersion = %s", hardwareVersion));
        logger.info(String.format("firmwareVersion = %s", firmwareVersion));
        logger.info(String.format("environment = %s", environment));
        logger.info(String.format("gnssVersion = %s", gnssVersion));
        logger.info(String.format("description = %s", description));
        logger.info(String.format("file = %s", file != null ? file.getOriginalFilename() : null));
        logger.info(String.format("appPlatform = %s", appPlatform));
        logger.info(String.format("appVersionCode = %s", appVersionCode));
        if (Strings.isNullOrEmpty(firmwareType)
                || Strings.isNullOrEmpty(hardwareVersion)
                || Strings.isNullOrEmpty(firmwareVersion)
                || Strings.isNullOrEmpty(environment)
                || Strings.isNullOrEmpty(gnssVersion)
                || file == null
                || file.isEmpty()
                || Strings.isNullOrEmpty(appPlatform)
                || Strings.isNullOrEmpty(appVersionCode)) {
            throw new DataFormatException();
        }
        if (!FirmwareEnvironmentEnum.TEST.getEnvironment().equals(environment) &&
                !FirmwareEnvironmentEnum.PROD.getEnvironment().equals(environment)) {
            throw new DataFormatException();
        }
    }

    /**
     * 获取test、 prod下面的数据（最多展示100条）
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
                map.put("appPlatform", item.getAppPlatform());
                map.put("appVersionCode", item.getAppVersionCode() + "");
                map.put("firmwareType", item.getFirmwareType());
                map.put("hardwareCode", item.getHardwareCode());
                map.put("version", item.getVersion());
                map.put("versionCode", String.valueOf(item.getVersionCode()));
                map.put("enable", item.getEnable() == 0 ? "不可用" : "可用");
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
                                          String versionCode,
                                          String appPlatform,
                                          String appVersionCode) {
        FirmwareInfo firmwareInfo = firmwareInfoMapper.getFirmwareDetail(firmwareType,
                hardwareCode, environment, versionCode, appPlatform, appVersionCode);
        if (firmwareInfo == null) {
            return new FirmwareInfo();
        }
        return firmwareInfo;
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

    /**
     * 固件降级（enable为1设置为0，使其不可用）
     *
     * @param id id
     */
    public void firmwareDowngrade(Integer id)
            throws FirmwareDisableException, VersionNotExistException, NoSuchAlgorithmException, KeyManagementException, IOException {
        FirmwareInfo firmwareInfo = firmwareInfoMapper.getFirmwareInfo(id);
        if (firmwareInfo == null) {
            throw new VersionNotExistException();
        }
        if (firmwareInfo.getEnable() != 1) {
            throw new FirmwareDisableException();
        }
        firmwareInfoMapper.setEnable(id, 0);
        firmwareInfo.setEnable(0);
        // 通知线上服务器对固件降级
        String configuation = firmwareTriggerParamConfigMapper.getFirmwareConfig();
        DefaultFirmwareUpgradeTrigger trigger = new DefaultFirmwareUpgradeTrigger();
        trigger.triggerFirmwareDowngrade(firmwareInfo, configuation);
    }

    /**
     * 固件激活（enable为0设置为1，使其可用）
     *
     * @param id id
     */
    public void firmwareActivate(Integer id)
            throws FirmwareDisableException, VersionNotExistException, NoSuchAlgorithmException, KeyManagementException, IOException {
        FirmwareInfo firmwareInfo = firmwareInfoMapper.getFirmwareInfo(id);
        if (firmwareInfo == null) {
            throw new VersionNotExistException();
        }
        if (firmwareInfo.getEnable() != 0) {
            throw new FirmwareDisableException();
        }
        firmwareInfoMapper.setEnable(id, 1);
        firmwareInfo.setEnable(1);
        // 通知线上服务器对固件激活
        trigger(firmwareInfo.getFirmwareType(), firmwareInfo.getHardwareCode(), firmwareInfo.getEnvironment(), firmwareInfo.getVersionCode(), firmwareInfo.getAppPlatform(), firmwareInfo.getVersionCode());
    }

    /**
     * 删除固件
     */
    public void firmwareDelete(Integer id) throws VersionNotExistException, FirmwareEnableException {
        FirmwareInfo firmwareInfo = firmwareInfoMapper.getFirmwareInfo(id);
        if (firmwareInfo == null) {
            throw new VersionNotExistException();
        }
        if (firmwareInfo.getEnable() == 1) {
            throw new FirmwareEnableException();
        }
        firmwareInfoMapper.delete(id);
    }

    /**
     * 触发升级
     */
    public void firmwareTrigger(Integer id)
            throws VersionNotExistException {
        FirmwareInfo firmwareInfo = firmwareInfoMapper.getFirmwareInfo(id);
        if (firmwareInfo == null) {
            throw new VersionNotExistException();
        }
        String configuation = firmwareTriggerParamConfigMapper.getFirmwareConfig();
        new Thread(() -> {
            String firmwareType = firmwareInfo.getFirmwareType();
            String hardwareCode = firmwareInfo.getHardwareCode();
            FirmwareEnvironmentEnum firmwareEnvironmentEnum = "test".equals(firmwareInfo.getEnvironment())
                    ? FirmwareEnvironmentEnum.TEST : FirmwareEnvironmentEnum.PROD;
            String versionCode = firmwareInfo.getVersionCode();
            FirmwareUpgradeContext firmwareUpgradeContext = new FirmwareUpgradeContext(firmwareType, hardwareCode,
                    firmwareEnvironmentEnum, versionCode, firmwareInfo, configuation);
            DefaultFirmwareUpgradeTrigger trigger = new DefaultFirmwareUpgradeTrigger();
            try {
                trigger.trigger(firmwareUpgradeContext);
            } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
                logger.info(ExceptionUtil.getErrorMessage(e));
            }
        }).start();
    }

    /**
     * 添加新的APP版本号处理逻辑
     */
    public void firmwareUpgradeWristbandAppAdd(String id,String appVersionCode)
            throws DataFormatException, VersionHasExistException {
        if (Strings.isNullOrEmpty(appVersionCode)){
            throw new DataFormatException();
        }
        FirmwareInfo firmwareInfo = firmwareInfoMapper.getFirmwareInfo(Integer.parseInt(id));
        firmwareInfo.setAppVersionCode(appVersionCode);
        if (firmwareInfoMapper.exist(firmwareInfo.getFirmwareType(), firmwareInfo.getHardwareCode(), firmwareInfo.getEnvironment(), firmwareInfo.getVersionCode(), firmwareInfo.getAppPlatform(), appVersionCode)) {
            throw new VersionHasExistException();
        }
        logger.info(firmwareInfo);
        firmwareInfoMapper.insert(firmwareInfo);
        // 触发升级
        trigger(firmwareInfo.getFirmwareType(), firmwareInfo.getHardwareCode(), firmwareInfo.getEnvironment(), firmwareInfo.getVersionCode(), firmwareInfo.getAppPlatform(), appVersionCode);
    }


}
