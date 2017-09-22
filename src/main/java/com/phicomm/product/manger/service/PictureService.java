package com.phicomm.product.manger.service;

import java.io.*;
import java.lang.String;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.base.Strings;
import com.phicomm.product.manger.dao.PictureMapper;
import com.phicomm.product.manger.dao.PictureUploadParamConfigMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.IdHasExistException;
import com.phicomm.product.manger.exception.UploadFileException;
import com.phicomm.product.manger.model.picture.PictureUpload;
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
public class PictureService {

    private PictureMapper pictureMapper;

    private static final String MAIN_DATA_SOURCE = "localDataSource";

    private static final String TEST_DATA_SOURCE = "testDataSource";

    private static final String PROD_DATA_SOURCE = "prodDataSource";

    private static final String type = TEST_DATA_SOURCE;

    private static final String HERMES_FILE_HTTP_URL_PREFIX = "http://114.141.173.17:2580/hermes/file/";

    private static String hermesTempDirPath = System.getProperty("java.io.tmpdir");

    private PictureUploadParamConfigMapper pictureUploadParamConfigMapper;


    @Autowired
    public PictureService(PictureMapper pictureMapper,  PictureUploadParamConfigMapper pictureUploadParamConfigMapper) {
        this.pictureMapper = pictureMapper;
        this.pictureUploadParamConfigMapper = pictureUploadParamConfigMapper;
        Assert.notNull(this.pictureMapper);
        Assert.notNull(this.pictureUploadParamConfigMapper);
    }

    /**
     * 图片上传
     */
    public void pictureUpload(PictureUpload pictureUpload)
            throws DataFormatException, IdHasExistException {
        selectDatabase(type);
        pictureMapper.pictureUpload(pictureUpload);
    }

    /**
     *图片压缩包保存
     * @param file
     * @param picId
     * @param picChiName
     * @param picEngName
     * @param picVersion
     * @throws DataFormatException
     * @throws IOException
     * @throws IdHasExistException
     */
    public void pictureUploadNumber(MultipartFile[] file, int[] picId, String[] picChiName, String[] picEngName, String picVersion) throws DataFormatException, IOException, IdHasExistException, KeyManagementException, NoSuchAlgorithmException, UploadFileException, SQLException {
        List<PictureUpload> pictures = new LinkedList<PictureUpload>();
        HashSet set = new HashSet();

        for (int id : picId) {
            set.add(id);
        }
        if (set.size() != picId.length) {
            throw new DataFormatException();

        }

        selectDatabase(type);
        pictureMapper.pictureDelete(picVersion);

        for (int i = 0; i < file.length; i++) {
            PictureUpload pictureUpload = new PictureUpload(picId[i], picEngName[i], picChiName[i], picVersion);
            Map<String, String> map = FileUtil.uploadFileToHermes(file[i]);
            pictureUpload.setPicUrl(map.get("url"));
            pictureUpload(pictureUpload);
            pictures.add(pictureUpload);
        }
        CustomerContextHolder.selectLocalDataSource();
        String param = pictureUploadParamConfigMapper.getPictureConfig();
        CustomerContextHolder.clearDataSource();
        JSONObject jsonObject = JSON.parseObject(param);
        String testCallbackUrl = String.valueOf(JSONPath.eval(jsonObject, "$.picture.testCallback")) + "/insert";
        String data = JSON.toJSONString(pictures);
        HttpsUtil.post(testCallbackUrl, data, Charsets.UTF_8.name());
     }

    /**
     * 查看对应版本下图片信息
     * @param picVerison
     * @return
     * @throws DataFormatException
     */
    public List<PictureUpload> pictureList(String picVerison) throws DataFormatException {
        selectDatabase(type);
        return pictureMapper.pictureList(picVerison);
    }

    /**
     *获取配置信息
     * @return
     */
    public String getPictureConfig() {
        String configuation = pictureUploadParamConfigMapper.getPictureConfig();
        return Strings.nullToEmpty(configuation);
    }

    /**
     *设置配置信息
     * @param configuation
     */
    public void setPictureConfig(String configuation) {
        pictureUploadParamConfigMapper.clean();
        pictureUploadParamConfigMapper.insert(configuation);
    }

    /**
     * 环境选择
     */
    private void selectDatabase(String type) {

        switch (type) {
            case TEST_DATA_SOURCE:
                CustomerContextHolder.selectTestDataSource();
                break;
            case PROD_DATA_SOURCE:
                CustomerContextHolder.selectProdDataSource();
                break;
            case MAIN_DATA_SOURCE:
                CustomerContextHolder.selectLocalDataSource();
                break;
            default:
                break;
        }
    }
}
