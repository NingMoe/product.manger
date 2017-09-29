package com.phicomm.product.manger.service;

import java.io.*;
import java.lang.String;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.base.Strings;
import com.phicomm.product.manger.dao.WatchPlatePictureUploadMapper;
import com.phicomm.product.manger.dao.WatchPlatePictureParamConfigMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.UploadFileException;
import com.phicomm.product.manger.model.ota.BalanceOtaInfo;
import com.phicomm.product.manger.model.watchplate.WatchPlatePictureUpload;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import com.phicomm.product.manger.utils.FileUtil;
import com.phicomm.product.manger.utils.HttpsUtil;
import org.apache.commons.io.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;



/**
 * Created by xiang.zhang on 2017/9/6.
 */
@Service
public class WatchPlatePictureService {

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
     * 表盘图片上传逻辑
     * @param watchPlatePictureUpload
     * @param environment
     */
    public void watchPlatePictureUpload(WatchPlatePictureUpload watchPlatePictureUpload, String environment) {
        selectDatabase(environment);
        watchPlatePictureUploadMapper.watchPlatePictureUpload(watchPlatePictureUpload);
    }

    /**
     * 表盘图片详情保存
     * @param file 图片文件
     * @param picId 图片编号
     * @param picChiName 图片中文名
     * @param picEngName 图片英文名
     * @param picVersion 图片版本
     * @param environment 表盘应用环境
     * @throws DataFormatException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws UploadFileException
     * @throws KeyManagementException
     */
    public void pictureUploadNumber(MultipartFile[] file, int[] picId, String[] picChiName, String[] picEngName, String picVersion,String picResolution, String environment) throws DataFormatException, IOException, NoSuchAlgorithmException, UploadFileException, KeyManagementException {
        List<WatchPlatePictureUpload> pictures = new LinkedList<WatchPlatePictureUpload>();
        Set set = new HashSet();
        for (int id : picId) {
            set.add(id);
        }
        if (set.size() != picId.length) {
            throw new DataFormatException();
        }
        selectDatabase(environment);
        watchPlatePictureUploadMapper.watchPlatePictureDelete(picVersion);
        for (int i = 0; i < file.length; i++) {
            WatchPlatePictureUpload watchPlatePictureUpload = new WatchPlatePictureUpload(picId[i], picEngName[i], picChiName[i], picVersion,picResolution);
            Map<String, String> map = FileUtil.uploadFileToHermes(file[i]);
            watchPlatePictureUpload.setPicUrl(map.get("url"));
            Date now = new Date();
            watchPlatePictureUpload.setCreateTime(now);
            watchPlatePictureUpload.setUpdateTime(now);
            watchPlatePictureUpload(watchPlatePictureUpload, environment);
            pictures.add(watchPlatePictureUpload);
        }
        selectDatabase(environment);
        String param = watchPlatePictureParamConfigMapper.getWatchPlateConfig();
        JSONObject jsonObject = JSON.parseObject(param);
        switch (environment) {
            case "local":
                String localCallbackUrl = String.valueOf(JSONPath.eval(jsonObject, "$.watchplate.localCallback")) + "/insert";
                String localdata = JSON.toJSONStringWithDateFormat(pictures, "yyyy-MM-dd HH:mm:ss");
                HttpsUtil.post(localCallbackUrl, localdata, Charsets.UTF_8.name());
                break;
            case "test":
                String testCallbackUrl = String.valueOf(JSONPath.eval(jsonObject, "$.watchplate.testCallback")) + "/insert";
                String testdata = JSON.toJSONStringWithDateFormat(pictures, "yyyy-MM-dd HH:mm:ss");
                HttpsUtil.post(testCallbackUrl, testdata, Charsets.UTF_8.name());
                break;
            case "prod":
                String prodCallbackUrl = String.valueOf(JSONPath.eval(jsonObject, "$.watchplate.prodCallback")) + "/insert";
                String proddata = JSON.toJSONStringWithDateFormat(pictures, "yyyy-MM-dd HH:mm:ss");
                HttpsUtil.post(prodCallbackUrl, proddata, Charsets.UTF_8.name());
                break;
            default:
                break;
        }
    }

    /**
     * 获取表盘图片列表
     * @return 返回图片信息
     */
    public List<WatchPlatePictureUpload> watchPlatePictureList() {
        return  watchPlatePictureUploadMapper.watchPlatePictureList();
    }

    /**
     *获取表盘配置信息
     * @return
     */
    public String getWatchPlateConfig() {
        String configuation = watchPlatePictureParamConfigMapper.getWatchPlateConfig();
        return Strings.nullToEmpty(configuation);
    }

    /**
     *设置表盘配置信息
     * @param configuation
     */
    public void setWatchPlateConfig(String configuation) {
        watchPlatePictureParamConfigMapper.clean();
        watchPlatePictureParamConfigMapper.insert(configuation);
    }

    /**
     * 表盘应用环境选择
     * @param environment
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
