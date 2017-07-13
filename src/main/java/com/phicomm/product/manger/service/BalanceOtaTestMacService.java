package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.BalanceOtaTestMacMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试mac
 * Created by wei.yang on 2017/7/13.
 */
@Service
public class BalanceOtaTestMacService {

    private BalanceOtaTestMacMapper balanceOtaTestMacMapper;

    @Autowired
    public BalanceOtaTestMacService(BalanceOtaTestMacMapper balanceOtaTestMacMapper) {
        this.balanceOtaTestMacMapper = balanceOtaTestMacMapper;
        Assert.notNull(this.balanceOtaTestMacMapper);
    }

    /**
     * 将需要写入的mac地址写入表中
     *
     * @param macString mac
     */
    public void insertMac(String macString, String environment) throws DataFormatException {
        List macList = formatMac(macString);
        if (macList.isEmpty()) {
            throw new DataFormatException();
        }
        if ("prod".equalsIgnoreCase(environment)) {
            CustomerContextHolder.selectProdDataSource();
        } else {
            CustomerContextHolder.selectTestDataSource();
        }
        balanceOtaTestMacMapper.cleanMac();
        balanceOtaTestMacMapper.insertBatch(macList);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 认为每个mac都是合法的、且都已空格分隔
     *
     * @param macString mac字符串
     * @return mac列表
     */
    private List<String> formatMac(String macString) {
        macString = macString.replaceAll("\\s+", "、");
        List<String> macList = Arrays.asList(macString.split("、"));
        for (int i = macList.size() - 1; i >= 0; i--) {
            if (macList.get(i).length() != 17) {
                macList.remove(i);
            }
        }
        return Arrays.asList(macString.split("、"));
    }

    /**
     * 格式化mac ：
     * 1.首先去除接收字符串中的所有空格及、，分隔符（因为不确定是以哪种方式对mac进行的分割）；
     * 2.通过：分割字符串
     * 3.当出现分割的数组中存在字节长为4的部分时，该部分即认为是两个mac的分隔特征。可能存在某多个连在一起的mac地址同时错误，导
     * 致拼接好的mac错误
     * 4.重新拼接
     *
     * @param macString 从字符串中拿取mac
     * @return mac列表
     */
    private List formatMacAbort(String macString) {
        List<String> macList = new ArrayList<>();
        macString = macString.replaceAll("\\s+", "").replaceAll("、", "").replaceAll(",", "");
        String[] splitMac = macString.split(":");
        String nextMacHeader = "";
        boolean start = true;
        StringBuilder macBuilder = null;
        for (String aSplitMac : splitMac) {
            if (start) {
                macBuilder = new StringBuilder();
            }
            if (!nextMacHeader.equalsIgnoreCase("")) {
                macBuilder.append(nextMacHeader).append(":");
            }
            if (aSplitMac.length() != 4) {
                start = false;
                macBuilder.append(aSplitMac).append(":");
                nextMacHeader = "";
            }
            if (aSplitMac.length() == 4) {
                macBuilder.append(aSplitMac.subSequence(0, 2));
                nextMacHeader = aSplitMac.substring(2, 4);
                start = true;
                if (macBuilder.length() == 17) {
                    macList.add(macBuilder.toString());
                }
            }
        }
        return macList;
    }
}
