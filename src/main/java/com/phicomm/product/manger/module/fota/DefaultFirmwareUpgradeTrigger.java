package com.phicomm.product.manger.module.fota;

/**
 * 默认的操作
 *
 * Created by yufei.liu on 2017/7/12.
 */
public class DefaultFirmwareUpgradeTrigger extends AbstractFirmwareUpgradeTrigger {

    @Override
    public void trigger(FirmwareUpgradeContext firmwareUpgradeContext) {
        getLogger().info("trigger fota firmware upgrade.");
        getLogger().info(firmwareUpgradeContext);
    }

}
