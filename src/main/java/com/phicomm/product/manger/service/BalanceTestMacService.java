package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.BalanceOtaTestMacMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 测试mac
 *
 * @author wei.yang on 2017/7/13.
 */
@Service
public class BalanceTestMacService {

    private BalanceOtaTestMacMapper balanceOtaTestMacMapper;

    @Autowired
    public BalanceTestMacService(BalanceOtaTestMacMapper balanceOtaTestMacMapper) {
        this.balanceOtaTestMacMapper = balanceOtaTestMacMapper;
        Assert.notNull(this.balanceOtaTestMacMapper);
    }

    /**
     * 将需要写入的mac地址写入表中
     *
     * @param firmwareType 固件类型
     * @param macString    mac
     * @param production   产品型号
     * @param environment  环境
     */
    public void insertMac(String macString, String environment, String production, String firmwareType)
            throws DataFormatException {
        List macList = formatMac(macString);
        if (macList.isEmpty()) {
            throw new DataFormatException();
        }
        if ("prod".equalsIgnoreCase(environment)) {
            CustomerContextHolder.selectProdDataSource();
        } else {
            CustomerContextHolder.selectTestDataSource();
        }
        balanceOtaTestMacMapper.cleanMac(production, firmwareType);
        balanceOtaTestMacMapper.insertBatch(macList, production.toLowerCase(), firmwareType);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 认为每个mac都是合法的、且都已、分隔:在程序中去除重复的mac地址
     *
     * @param macString mac字符串
     * @return mac列表
     */
    private List<String> formatMac(String macString) {
        System.out.println(macString);
        List<String> macList = new ArrayList<>();
        String[] macArray = macString.split("、");
        for (String mac : macArray) {
            if (mac.length() == 17) {
                macList.add(mac.toLowerCase());
            }
        }
        Set<String> macSet = new HashSet<>(macList);
        macList.clear();
        macList.addAll(macSet);
        return macList;
    }
}
