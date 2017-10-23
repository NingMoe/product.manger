package com.phicomm.product.manger.module.fota;

import com.phicomm.product.manger.exception.FirmwareTriggerFailException;
import com.phicomm.product.manger.model.firmware.FirmwareInfo;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * 触发升级的接口
 * <p>
 * Created by yufei.liu on 2017/7/12.
 */
public interface FirmwareUpgradeTrigger {

    /**
     * 触发firmwareUpgrade上下文
     * 1、首先判断是否固件类型匹配
     * 2、然后判断是测试环境还是生产环境，根据param选择相应的地址进行触发
     *
     * @param firmwareUpgradeContext 上下文
     */
    void trigger(FirmwareUpgradeContext firmwareUpgradeContext) throws NoSuchAlgorithmException, KeyManagementException, IOException, FirmwareTriggerFailException;

    /**
     * 触发固件降级
     */
    void triggerFirmwareDowngrade(FirmwareInfo firmwareInfo, String param) throws NoSuchAlgorithmException, KeyManagementException, IOException, FirmwareTriggerFailException;

    /**
     * 获得日志对象
     */
    Logger getLogger();

}
