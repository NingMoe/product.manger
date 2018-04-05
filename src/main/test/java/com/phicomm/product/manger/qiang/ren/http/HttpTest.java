package com.phicomm.product.manger.qiang.ren.http;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.phicomm.product.manger.model.mac.BalanceDeviceModel;
import com.phicomm.product.manger.utils.HttpsUtil;
import org.junit.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Http测试
 *
 * Created by qiang.ren on 2018/3/28.
 */
public class HttpTest {

    @Test
    public void httpsPost() throws NoSuchAlgorithmException, KeyManagementException, IOException {
        //String url = "https://114.141.173.15:20020/balance-app/sold/mac/status";
        //String url = "https://uss7.phicomm.com:8443/balance-app/sold/mac/status";
        String url = "https://eus7.phicomm.com:8443/balance-app/sold/mac/status";
        Map<String, String> header = Maps.newHashMap();
        header.put("Content-Type", "application/json");
        header.put("phicomm-language", "CH");
        header.put("phicomm-zone", "EAST-8");
        BalanceDeviceModel balanceDeviceModel = new BalanceDeviceModel("s7");
        balanceDeviceModel.setMac("2C3AE83558A0");
        balanceDeviceModel.setSn("CAAAAAAAAAAAAAA");
        byte[] content = HttpsUtil.post(url, header, JSON.toJSONString(balanceDeviceModel), "utf-8");
        if(content != null) {
            System.out.println(new String(content));
        }
    }
}
