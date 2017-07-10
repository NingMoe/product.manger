package com.phicomm.product.manger.service;

import com.google.common.base.Strings;
import com.phicomm.product.manger.dao.BalanceOtaMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.ota.BalanceOtaInfo;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import com.phicomm.product.manger.utils.CRC16Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * ota
 * Created by wei.yang on 2017/7/10.
 */
@Service
public class BalanceOtaService {

    private static final Logger logger = Logger.getLogger(BalanceOtaService.class);

    private HermesService hermesService;

    private BalanceOtaMapper balanceOtaMapper;


    @Autowired
    public BalanceOtaService(BalanceOtaMapper balanceOtaMapper,
                             HermesService hermesService) {
        this.balanceOtaMapper = balanceOtaMapper;
        this.hermesService = hermesService;
        Assert.notNull(this.balanceOtaMapper);
        Assert.notNull(this.hermesService);
    }

    /**
     * 上传Ota版本信息
     *
<<<<<<< Updated upstream
     * @param aFile       a版本
     * @param bFile       b版本
     * @param version     版本号
     * @param environment 环境
=======
     * @param aFile   a版本
     * @param bFile   b版本
     * @param version 版本号
>>>>>>> Stashed changes
     * @return ota信息
     * @throws IOException         io异常
     * @throws DataFormatException 数据格式异常
     */
    public BalanceOtaInfo uploadOtaMessage(MultipartFile aFile, MultipartFile bFile, int version, String environment)
            throws IOException,
            DataFormatException {
        logger.info(environment);
        checkParamFormat(aFile, bFile, version);
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
        if (Strings.isNullOrEmpty(environment) || "prod".equalsIgnoreCase(environment)) {
            CustomerContextHolder.selectProdDataSource();
        } else {
            CustomerContextHolder.selectLocalDataSource();
        }
        CustomerContextHolder.selectProdDataSource();
        int effectLine = balanceOtaMapper.uploadOtaMessage(balanceOtaInfo);
        CustomerContextHolder.clearDataSource();
        if (effectLine != 0) {
            return balanceOtaInfo;
        }
        return null;
    }

    /**
     * 核对参数格式
     *
     * @param aFile   a版本文件
     * @param bFile   b版本文件
     * @param version 版本号
     * @throws DataFormatException 数据格式异常
     */
    private void checkParamFormat(MultipartFile aFile, MultipartFile bFile, int version) throws DataFormatException {
        if (aFile.isEmpty() || bFile.isEmpty() || version <= 0) {
            throw new DataFormatException();
        }
    }
}
