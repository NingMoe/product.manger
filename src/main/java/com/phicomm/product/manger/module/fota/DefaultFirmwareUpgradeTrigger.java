package com.phicomm.product.manger.module.fota;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.base.Charsets;
import com.phicomm.product.manger.utils.HttpsUtil;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * 默认的操作
 *
 * Created by yufei.liu on 2017/7/12.
 */
public class DefaultFirmwareUpgradeTrigger extends AbstractFirmwareUpgradeTrigger {

    @Override
    public void trigger(FirmwareUpgradeContext firmwareUpgradeContext) throws NoSuchAlgorithmException,
            KeyManagementException, IOException {
        getLogger().info("trigger fota firmware upgrade.");
        getLogger().info(firmwareUpgradeContext);
        String param = firmwareUpgradeContext.getParam();
        JSONObject jsonObject = JSON.parseObject(param);
        String testCallbackUrl = String.valueOf(JSONPath.eval(jsonObject, "$.wristband.testCallback"));
        String prodCallbackUrl = String.valueOf(JSONPath.eval(jsonObject, "$.wristband.prodCallback"));
        if(firmwareUpgradeContext.isTestEnvironment()) {
            HttpsUtil.post(testCallbackUrl, firmwareUpgradeContext.getFirmwareData(), Charsets.UTF_8.name());
        }
        if(firmwareUpgradeContext.isProdEnvironment()) {
            HttpsUtil.post(prodCallbackUrl, firmwareUpgradeContext.getFirmwareData(), Charsets.UTF_8.name());
        }
    }

}
