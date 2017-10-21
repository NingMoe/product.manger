package com.phicomm.product.manger.module.fota;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.base.Strings;
import com.phicomm.product.manger.enumeration.FirmwareEnvironmentEnum;
import com.phicomm.product.manger.enumeration.RequestType;
import com.phicomm.product.manger.model.firmware.FirmwareInfo;
import com.phicomm.product.manger.utils.HttpUtil;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * 默认的操作
 *
 * @author yufei.liu on 2017/7/12.
 */
public class DefaultFirmwareUpgradeTrigger extends AbstractFirmwareUpgradeTrigger {

    private static final String CONTENT_TYPE = "application/json";

    @Override
    public void trigger(FirmwareUpgradeContext firmwareUpgradeContext) throws NoSuchAlgorithmException,
            KeyManagementException, IOException {
        getLogger().info("trigger fota firmware upgrade.");
        getLogger().info(firmwareUpgradeContext);
        String param = firmwareUpgradeContext.getParam();
        JSONObject jsonObject = JSON.parseObject(param);
        String testCallbackUrl = String.valueOf(JSONPath.eval(jsonObject, "$.wristband.testCallback"));
        String prodCallbackUrl = String.valueOf(JSONPath.eval(jsonObject, "$.wristband.prodCallback"));
        String data = firmwareUpgradeContext.getFirmwareData();
        getLogger().info(data);
        if (firmwareUpgradeContext.isTestEnvironment() && !Strings.isNullOrEmpty(testCallbackUrl)) {
            getLogger().info(String.format("testCallbackUrl is %s.", testCallbackUrl));
            HttpUtil.openRequestUrl(testCallbackUrl, RequestType.POST.getKeyName(), CONTENT_TYPE, JSONObject.parseObject(data));
        }
        if (firmwareUpgradeContext.isProdEnvironment() && !Strings.isNullOrEmpty(prodCallbackUrl)) {
            getLogger().info(String.format("prodCallbackUrl is %s.", prodCallbackUrl));
            HttpUtil.openRequestUrl(prodCallbackUrl, RequestType.POST.getKeyName(), CONTENT_TYPE, JSONObject.parseObject(data));
        }
    }

    /**
     * 触发固件降级
     */
    @Override
    public void triggerFirmwareDowngrade(FirmwareInfo firmwareInfo, String param) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        getLogger().info("trigger fota firmware downgrade.");
        getLogger().info(firmwareInfo);
        JSONObject jsonObject = JSON.parseObject(param);
        String testDowngradeCallback = String.valueOf(JSONPath.eval(jsonObject, "$.wristband.testDowngradeCallback"));
        String prodDowngradeCallback = String.valueOf(JSONPath.eval(jsonObject, "$.wristband.prodDowngradeCallback"));
        boolean isTestEnvironment = FirmwareEnvironmentEnum.TEST.getEnvironment().equals(firmwareInfo.getEnvironment());
        boolean isProdEnvironment = FirmwareEnvironmentEnum.PROD.getEnvironment().equals(firmwareInfo.getEnvironment());
        if (isTestEnvironment && !Strings.isNullOrEmpty(testDowngradeCallback)) {
            getLogger().info(String.format("testDowngradeCallback is %s.", testDowngradeCallback));
            HttpUtil.openRequestUrl(testDowngradeCallback, RequestType.POST.getKeyName(), CONTENT_TYPE, (JSONObject) JSONObject.toJSON(firmwareInfo));
        }
        if (isProdEnvironment && !Strings.isNullOrEmpty(prodDowngradeCallback)) {
            getLogger().info(String.format("prodDowngradeCallback is %s.", prodDowngradeCallback));
            HttpUtil.openRequestUrl(prodDowngradeCallback, RequestType.POST.getKeyName(), CONTENT_TYPE, (JSONObject) JSONObject.toJSON(firmwareInfo));
        }
    }

}
