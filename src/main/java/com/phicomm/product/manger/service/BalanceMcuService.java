package com.phicomm.product.manger.service;

import com.google.common.base.Strings;
import com.phicomm.product.manger.dao.BalanceMcuMapper;
import com.phicomm.product.manger.enumeration.TriggerTypeEnum;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.mcu.BalanceMcuBean;
import com.phicomm.product.manger.model.mcu.BalanceMcuStatus;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import com.phicomm.product.manger.utils.CRC16Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.HostAndPort;

import java.io.IOException;
import java.util.List;

/**
 * mcu
 * Created by wei.yang on 2017/7/17.
 */
@Service
public class BalanceMcuService {

    private BalanceMcuMapper balanceMcuMapper;

    private OtaServerService otaServerService;

    private HermesService hermesService;

    @Autowired
    public BalanceMcuService(BalanceMcuMapper balanceMcuMapper,
                             OtaServerService otaServerService,
                             HermesService hermesService) {
        this.hermesService = hermesService;
        this.otaServerService = otaServerService;
        this.balanceMcuMapper = balanceMcuMapper;
        Assert.notNull(this.hermesService);
        Assert.notNull(this.otaServerService);
        Assert.notNull(this.balanceMcuMapper);
    }

    /**
     * 上传mcu版本信息
     *
     * @param file        文件
     * @param version     版本
     * @param environment 环境
     * @return mcu版本信息
     * @throws IOException         上传失败
     * @throws DataFormatException 数据格式异常
     */
    public BalanceMcuBean uploadMcuMessage(MultipartFile file, int version, String environment)
            throws IOException,
            DataFormatException {
        checkMcuParamFormat(file, version);
        BalanceMcuBean balanceMcuBean = new BalanceMcuBean();
        int aFileCrc = CRC16Util.calcCrc16(file.getBytes(), 0, file.getBytes().length);
        String fileUrl = (String) hermesService.uploadFile(file).get("fileHttpsUrl");
        if (Strings.isNullOrEmpty(fileUrl)) {
            throw new IOException();
        }
        balanceMcuBean.setCrc(aFileCrc);
        balanceMcuBean.setFileUrl(fileUrl);
        balanceMcuBean.setVersion(version);
        if ("prod".equalsIgnoreCase(environment)) {
            CustomerContextHolder.selectProdDataSource();
        } else {
            CustomerContextHolder.selectTestDataSource();
        }
        int effectLine = balanceMcuMapper.uploadMcuMessage(balanceMcuBean);
        CustomerContextHolder.clearDataSource();
        if (effectLine != 0) {
            return balanceMcuBean;
        }
        return null;
    }

    /**
     * 更新版本状态
     *
     * @param balanceMcuStatus mcu版本信息
     * @throws DataFormatException 数据格式错误
     */
    public void updateBalanceMcuStatus(BalanceMcuStatus balanceMcuStatus) throws DataFormatException {
        checkMcuStatusParamFormat(balanceMcuStatus);
        String environment = balanceMcuStatus.getEnvironment();
        if ("prod".equalsIgnoreCase(environment)) {
            CustomerContextHolder.selectProdDataSource();
        } else {
            CustomerContextHolder.selectTestDataSource();
        }
        balanceMcuMapper.modifyMcuVersionStatus(balanceMcuStatus);
        if (balanceMcuStatus.getEnable() == 1) {
            balanceMcuStatus.setEnable(0);
            balanceMcuMapper.cleanMcuStatus(balanceMcuStatus);
        }
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 修改状态并触发
     *
     * @param balanceMcuStatus 要修改的版本状态
     * @return 触发失败的主机
     * @throws DataFormatException 数据格式异常
     * @throws IOException         io异常
     */
    public List<HostAndPort> updateStatusAndTrigger(BalanceMcuStatus balanceMcuStatus) throws DataFormatException,
            IOException {
        updateBalanceMcuStatus(balanceMcuStatus);
        return otaServerService.updateTrigger(TriggerTypeEnum.MCU);
    }

    /**
     * 获取版本列表
     *
     * @param environment 环境信息
     * @return 版本列表
     */
    public List<BalanceMcuBean> fetchMcuList(String environment) {
        List<BalanceMcuBean> balanceMcuBeanList;
        if ("prod".equalsIgnoreCase(environment)) {
            CustomerContextHolder.selectProdDataSource();
        } else {
            CustomerContextHolder.selectTestDataSource();
        }
        balanceMcuBeanList = balanceMcuMapper.fetchMcuList();
        CustomerContextHolder.clearDataSource();
        return balanceMcuBeanList;
    }

    /**
     * 获取mcu格式化好的列表信息
     *
     * @param environment 环境
     * @return JSONArray格式的版本信息
     */
    public List<BalanceMcuBean> fetchMcuVersionList(String environment) {
        return fetchMcuList(environment);
    }

    /**
     * 核查参数格式
     *
     * @param file    文件
     * @param version 版本信息
     * @throws DataFormatException 数据格式异常
     */
    private void checkMcuParamFormat(MultipartFile file, int version) throws DataFormatException {
        if (file.isEmpty() || version <= 0) {
            throw new DataFormatException();
        }
    }

    /**
     * 检查版本修改信息的格式
     *
     * @param balanceMcuStatus mcu状态信息
     * @throws DataFormatException 数据格式异常
     */
    private void checkMcuStatusParamFormat(BalanceMcuStatus balanceMcuStatus) throws DataFormatException {
        if (balanceMcuStatus == null) {
            throw new DataFormatException();
        }
        int enable = balanceMcuStatus.getEnable();
        int testing = balanceMcuStatus.getTesting();
        int version = balanceMcuStatus.getVersion();
        if (enable != 1 && enable != 0 || testing != 1 && testing != 0 || version <= 0) {
            throw new DataFormatException();
        }
    }
}
