package com.phicomm.product.manger.service;

import com.google.common.base.Strings;
import com.phicomm.product.manger.dao.FirmwareInfoMapper;
import com.phicomm.product.manger.enumeration.FirmwareEnvironmentEnum;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.UploadFileException;
import com.phicomm.product.manger.model.firmware.FirmwareInfo;
import com.phicomm.product.manger.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * 固件升级处理逻辑
 * <p>
 * Created by yufei.liu on 2017/7/10.
 */
@Service
public class FirmwareUpgradeService {

    private FirmwareInfoMapper firmwareInfoMapper;

    @Autowired
    public FirmwareUpgradeService(FirmwareInfoMapper firmwareInfoMapper) {
        this.firmwareInfoMapper = firmwareInfoMapper;
        Assert.notNull(this.firmwareInfoMapper);
    }

    /**
     * 文件上传
     */
    public void firmwareUpgradeWristbandFileUpload(String firmwareType,
                                                   String hardwareVersion,
                                                   String firmwareVersion,
                                                   String environment,
                                                   MultipartFile file) throws DataFormatException, UploadFileException {
        // 校验参数
        checkFirmwareUpgradeWristbandFileUpload(firmwareType, hardwareVersion, firmwareVersion, environment, file);
        // 上传文件
        String downloadUrl = FileUtil.uploadFileToHermes(file);
        FirmwareInfo firmwareInfo = new FirmwareInfo();
        firmwareInfo.setFirmwareType(firmwareType);
        firmwareInfo.setHardwareCode(hardwareVersion);
        firmwareInfo.setVersion(file.getOriginalFilename());
        firmwareInfo.setVersionCode(Integer.parseInt(firmwareVersion));
        firmwareInfo.setEnvironment(environment);
        firmwareInfo.setTest(1);
        firmwareInfo.setEnable(0);
        firmwareInfo.setUrl(downloadUrl);
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
                                                        MultipartFile file) throws DataFormatException {
        if(Strings.isNullOrEmpty(firmwareType)
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
        if(!FirmwareEnvironmentEnum.TEST.name().equals(environment) &&
                !FirmwareEnvironmentEnum.PROD.name().equals(environment)) {
            throw new DataFormatException();
        }
    }

}
