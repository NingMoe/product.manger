package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.mac.BalanceDeviceModel;
import com.phicomm.product.manger.utils.HttpsUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * MAC业务逻辑
 *
 * @author qiang.ren
 * @date 2018/3/28
 */
@Service
public class MacService {

    private static final Logger logger = Logger.getLogger(MacService.class);

    private static final String TEST = "https://114.141.173.15:20020/balance-app/sold/mac/status";

    private static final String USA = "https://114.141.173.15:20020/balance-app/sold/mac/status";

    private static final String GERMANY = "https://114.141.173.15:20020/balance-app/sold/mac/status";

    /**
     * 查询该mac地址对应的体脂称是否售出
     *
     * @param balanceDeviceModel mac和sn类
     * @return 已售出：true、未售出：false
     */
    public boolean getMacStatus(BalanceDeviceModel balanceDeviceModel, String environment) throws NoSuchAlgorithmException,
            KeyManagementException, IOException, DataFormatException {
        if (null == balanceDeviceModel || Strings.isNullOrEmpty(environment)){
            throw new DataFormatException();
        }
        String url = null;
        switch (environment) {
            case "test":
                url = TEST;
                break;
            case "usa":
                url = USA;
                break;
            case "germany":
                url = GERMANY;
                break;
            default:
        }
        Map<String, String> header = Maps.newHashMap();
        header.put("Content-Type", "application/json");
        header.put("phicomm-language", "CH");
        header.put("phicomm-zone", "EAST-8");
        balanceDeviceModel.setMac("2C3AE83558A0");
        balanceDeviceModel.setSn("CAAAAAAAAAAAAAA");
        byte[] content = HttpsUtil.post(url, header, JSON.toJSONString(balanceDeviceModel), "utf-8");
        if (content == null) {
            throw new NoSuchAlgorithmException();
        }
        logger.info("content:" + new String(content));
        return (boolean) JSONObject.parseObject(new String(content)).get("data");
    }
}
