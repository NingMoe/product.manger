package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.base.Strings;
import com.phicomm.product.manger.dao.WatchPlatePictureParamConfigMapper;
import com.phicomm.product.manger.dao.WatchPlatePictureUploadMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.UploadFileException;
import com.phicomm.product.manger.model.watchplate.WatchPlatePictureUpload;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import com.phicomm.product.manger.utils.ExceptionUtil;
import com.phicomm.product.manger.utils.FileUtil;
import com.phicomm.product.manger.utils.HttpsUtil;
import org.apache.commons.io.Charsets;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by xiang.zhang on 2017/9/6.
 */
@Service
public class WatchPlatePictureService {

    private static final Logger logger = Logger.getLogger(WatchPlatePictureService.class);

    private WatchPlatePictureUploadMapper watchPlatePictureUploadMapper;

    private static final String TEST = "test";

    private static final String PROD = "prod";

    private static final String LOCAL = "local";

    private WatchPlatePictureParamConfigMapper watchPlatePictureParamConfigMapper;

    @Autowired
    public WatchPlatePictureService(WatchPlatePictureUploadMapper watchPlatePictureUploadMapper, WatchPlatePictureParamConfigMapper watchPlatePictureParamConfigMapper) {
        this.watchPlatePictureUploadMapper = watchPlatePictureUploadMapper;
        this.watchPlatePictureParamConfigMapper = watchPlatePictureParamConfigMapper;
        Assert.notNull(this.watchPlatePictureUploadMapper);
        Assert.notNull(this.watchPlatePictureParamConfigMapper);
    }

    /**
     * 表盘图片详情保存
     *
     * @param file        图片文件
     * @param picId       图片编号
     * @param picChiName  图片中文名
     * @param picEngName  图片英文名
     * @param picVersion  图片版本
     * @param environment 表盘应用环境
     * @throws DataFormatException 数据格式错误
     * @throws UploadFileException 上传文件失败
     */
    public void pictureUploadNumber(MultipartFile[] file,
                                    Integer[] picId,
                                    String[] picChiName,
                                    String[] picEngName,
                                    String picVersion,
                                    String[] picResolution,
                                    String environment)
            throws DataFormatException, UploadFileException {
        selectDatabase(environment);
        watchPlatePictureUploadMapper.watchPlatePictureDelete(picVersion);
        List<WatchPlatePictureUpload> watchPlatePictureList = new LinkedList<>();
        for (int i = 0; i < file.length; i++) {
            WatchPlatePictureUpload watchPlatePictureUpload = new WatchPlatePictureUpload(picId[i], picEngName[i], picChiName[i], picVersion, picResolution[i]);
            Map<String, String> map = FileUtil.uploadFileToHermes(file[i]);
            watchPlatePictureUpload.setPicUrl(map.get("url"));
            Date now = new Date();
            watchPlatePictureUpload.setCreateTime(now);
            watchPlatePictureUpload.setUpdateTime(now);
            watchPlatePictureUploadMapper.watchPlatePictureUpload(watchPlatePictureUpload);
            watchPlatePictureList.add(watchPlatePictureUpload);
        }
        String param = watchPlatePictureParamConfigMapper.getWatchPlateConfig();
        JSONObject jsonObject = JSON.parseObject(param);
        String callbackUrl = "";
        switch (environment) {
            case "local":
                callbackUrl = String.valueOf(JSONPath.eval(jsonObject, "$.watchplate.localCallback")) + "/insert";
                break;
            case "test":
                callbackUrl = String.valueOf(JSONPath.eval(jsonObject, "$.watchplate.testCallback")) + "/insert";
                break;
            case "prod":
                callbackUrl = String.valueOf(JSONPath.eval(jsonObject, "$.watchplate.prodCallback")) + "/insert";
                break;
            default:
                break;
        }
        String data = JSON.toJSONStringWithDateFormat(watchPlatePictureList, "yyyy-MM-dd HH:mm:ss");
        try {
            HttpsUtil.post(callbackUrl, data, Charsets.UTF_8.name());
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            logger.info(ExceptionUtil.getErrorMessage(e));
        }
    }

    /**
     * 获取表盘图片列表
     *
     * @return 返回图片信息
     */
    public List<WatchPlatePictureUpload> watchPlatePictureList() {
        return watchPlatePictureUploadMapper.watchPlatePictureList();
    }

    /**
     * 获取表盘配置信息
     *
     * @return 返回配置详情
     */
    public String getWatchPlateConfig() {
        String configuration = watchPlatePictureParamConfigMapper.getWatchPlateConfig();
        return Strings.nullToEmpty(configuration);
    }

    /**
     * 设置表盘配置信息
     *
     * @param configuration 配置参数
     */
    public void setWatchPlateConfig(String configuration) {
        watchPlatePictureParamConfigMapper.clean();
        watchPlatePictureParamConfigMapper.insert(configuration);
    }

    /**
     * 表盘应用环境选择
     *
     * @param environment 应用环境选择参数
     */
    private void selectDatabase(String environment) {
        switch (environment) {
            case TEST:
                CustomerContextHolder.selectTestDataSource();
                break;
            case PROD:
                CustomerContextHolder.selectProdDataSource();
                break;
            case LOCAL:
                CustomerContextHolder.selectLocalDataSource();
                break;
            default:
                break;
        }
    }


}
