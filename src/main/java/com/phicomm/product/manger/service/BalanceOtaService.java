package com.phicomm.product.manger.service;

import com.google.common.base.Strings;
import com.phicomm.product.manger.dao.BalanceOtaMapper;
import com.phicomm.product.manger.enumeration.TriggerTypeEnum;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.ota.BalanceOtaInfo;
import com.phicomm.product.manger.model.ota.BalanceOtaStatus;
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
 * ota
 * Created by wei.yang on 2017/7/10.
 */
@Service
public class BalanceOtaService {

    private BalanceOtaMapper balanceOtaMapper;

    private OtaServerService otaServerService;

    private HermesService hermesService;

    @Autowired
    public BalanceOtaService(BalanceOtaMapper balanceOtaMapper,
                             OtaServerService otaServerService,
                             HermesService hermesService) {
        this.balanceOtaMapper = balanceOtaMapper;
        this.otaServerService = otaServerService;
        this.hermesService = hermesService;
        Assert.notNull(this.balanceOtaMapper);
        Assert.notNull(this.otaServerService);
        Assert.notNull(this.hermesService);
    }

    /**
     * 上传Ota版本信息，默认environment为test
     *
     * @param aFile       a版本
     * @param bFile       b版本
     * @param version     版本号
     * @param environment 环境
     * @return ota信息
     * @throws IOException         io异常
     * @throws DataFormatException 数据格式异常
     */
    public BalanceOtaInfo uploadOtaMessage(MultipartFile aFile, MultipartFile bFile, int version, String environment)
            throws IOException, DataFormatException {
        checkOtaParamFormat(aFile, bFile, version);
        BalanceOtaInfo balanceOtaInfo = new BalanceOtaInfo();
        int aFileCrc = CRC16Util.calcCrc16(aFile.getBytes(), 0, aFile.getBytes().length);
        int bFieCrc = CRC16Util.calcCrc16(bFile.getBytes(), 0, bFile.getBytes().length);
        String aFileUrl = (String) hermesService.uploadFile(aFile).get("fileHttpUrl");
        String bFileUrl = (String) hermesService.uploadFile(bFile).get("fileHttpUrl");
        if (Strings.isNullOrEmpty(aFileUrl) || Strings.isNullOrEmpty(bFileUrl)) {
            throw new IOException();
        }
        balanceOtaInfo.setaVersionCrc(aFileCrc);
        balanceOtaInfo.setbVersionCrc(bFieCrc);
        balanceOtaInfo.setaVersionFileUrl(aFileUrl);
        balanceOtaInfo.setbVersionFileUrl(bFileUrl);
        balanceOtaInfo.setSoftwareVersion(version);
        if ("prod".equalsIgnoreCase(environment)) {
            CustomerContextHolder.selectProdDataSource();
        } else {
            CustomerContextHolder.selectTestDataSource();
        }
        int effectLine = balanceOtaMapper.uploadOtaMessage(balanceOtaInfo);
        CustomerContextHolder.clearDataSource();
        if (effectLine != 0) {
            return balanceOtaInfo;
        }
        return null;
    }

    /**
     * 更新版本状态:当状态信息中的enable为1的时候顺带清理一下其他版本的状态，默认environment为test
     *
     * @param balanceOtaStatus 版本状态信息
     * @throws DataFormatException 数据格式异常
     */
    public void updateBalanceOtaStatus(BalanceOtaStatus balanceOtaStatus) throws DataFormatException {
        checkOtaStatusParamFormat(balanceOtaStatus);
        String environment = balanceOtaStatus.getEnvironment();
        if ("prod".equalsIgnoreCase(environment)) {
            CustomerContextHolder.selectProdDataSource();
        } else {
            CustomerContextHolder.selectTestDataSource();
        }
        balanceOtaMapper.updateOtaStatus(balanceOtaStatus);
        if (balanceOtaStatus.getEnable() == 1) {
            balanceOtaStatus.setEnable(0);
            balanceOtaMapper.cleanOtaStatus(balanceOtaStatus);
        }
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 修改版本状态并触发升级
     *
     * @param balanceOtaStatus 版本状态
     * @return 触发失败的ip
     * @throws DataFormatException 数据格式异常
     * @throws IOException         读异常
     */
    public List<HostAndPort> updateStatusAndTrigger(BalanceOtaStatus balanceOtaStatus) throws DataFormatException,
            IOException {
        updateBalanceOtaStatus(balanceOtaStatus);
        return otaServerService.updateTrigger(TriggerTypeEnum.OTA);
    }

    /**
     * 获取某个环境下的
     *
     * @param environment 环境，分为测试环境和生产环境
     * @return 信息列表
     */
    public List<BalanceOtaInfo> fetchOtaList(String environment) {
        List<BalanceOtaInfo> balanceOtaInfoList;
        if ("prod".equalsIgnoreCase(environment)) {
            CustomerContextHolder.selectProdDataSource();
        } else {
            CustomerContextHolder.selectTestDataSource();
        }
        balanceOtaInfoList = balanceOtaMapper.fetchOtaList();
        CustomerContextHolder.clearDataSource();
        return balanceOtaInfoList;
    }

    /**
     * 获取JSONObject格式的结果
     *
     * @param environment 环境
     * @return 版本列表
     */
    public List<BalanceOtaInfo> fetchOtaVersionList(String environment) {
        return fetchOtaList(environment);
    }

    /**
     * 核对参数格式
     *
     * @param aFile   a版本文件
     * @param bFile   b版本文件
     * @param version 版本号
     * @throws DataFormatException 数据格式异常
     */
    private void checkOtaParamFormat(MultipartFile aFile, MultipartFile bFile, int version) throws DataFormatException {
        if (aFile.isEmpty() || bFile.isEmpty() || version <= 0) {
            throw new DataFormatException();
        }
    }

    /**
     * 检查更新Ota状态的参数格式
     *
     * @param balanceOtaStatus Ota状态
     * @throws DataFormatException 数据格式异常
     */
    private void checkOtaStatusParamFormat(BalanceOtaStatus balanceOtaStatus) throws DataFormatException {
        if (balanceOtaStatus == null) {
            throw new DataFormatException();
        }
        int enable = balanceOtaStatus.getEnable();
        int testing = balanceOtaStatus.getTesting();
        int version = balanceOtaStatus.getVersion();
        if (enable != 1 && enable != 0) {
            throw new DataFormatException();
        }
        if (testing != 1 && testing != 0) {
            throw new DataFormatException();
        }
        if (version <= 0) {
            throw new DataFormatException();
        }
    }
}
