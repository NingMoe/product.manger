package com.phicomm.product.manger.module.fota;

import org.apache.log4j.Logger;

/**
 * 实现logger接口
 *
 * Created by yufei.liu on 2017/7/12.
 */
public abstract class AbstractFirmwareUpgradeTrigger implements FirmwareUpgradeTrigger {

    private static final Logger logger = Logger.getLogger(FirmwareUpgradeTrigger.class);

    /**
     * 获得日志对象
     */
    @Override
    public Logger getLogger() {
        return logger;
    }

}
