package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.phicomm.product.manger.dao.EssayMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.IdHasExistException;
import com.phicomm.product.manger.model.essay.EssayInfo;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 文章接口处理逻辑
 * Created by Qiang on 2017/8/7.
 */
@Service
public class EssayService {

    private EssayMapper essayMapper;

    private static final String TEST = "test";

    private static final String PROD = "prod";

    @Autowired
    public EssayService(EssayMapper essayMapper) {
        this.essayMapper = essayMapper;
        Assert.notNull(this.essayMapper);
    }

    /**
     * 文章新增处理逻辑
     *
     * @param essayId    用户自定义文章ID
     * @param title      文章标题
     * @param subtitle   文章副标题
     * @param summary    文章简介
     * @param coverUrl   文章封面URL
     * @param contentUrl 文章正文URL
     * @param type        环境类型 test或prod
     */
    public void essayUpload(String essayId, String title, String subtitle, String summary,
                            String coverUrl, String contentUrl, String type)
            throws DataFormatException, IdHasExistException {
        checkDataFormat(new String[]{essayId, title, subtitle, summary, coverUrl, contentUrl});
        EssayInfo essayInfo = new EssayInfo(essayId, title, subtitle, summary, coverUrl, contentUrl,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        selectDatabase(type);
        if (essayMapper.existEssay(essayId)) {
            throw new IdHasExistException();
        }
        essayMapper.essayUpload(essayInfo);
        CustomerContextHolder.clearDataSource();

    }

    /**
     * 文章更新处理逻辑
     *
     * @param essayId    用户自定义文章ID
     * @param title      文章标题
     * @param subtitle   文章副标题
     * @param summary    文章简介
     * @param coverUrl   文章封面URL
     * @param contentUrl 文章正文URL
     * @param type        环境类型 test或prod
     */
    public void essayUpdate(String essayId, String title, String subtitle, String summary,
                            String coverUrl, String contentUrl, String type)
            throws DataFormatException, IdHasExistException {
        checkDataFormat(new String[]{essayId, title, subtitle, summary, coverUrl, contentUrl});
        EssayInfo essayInfo = new EssayInfo(essayId, title, subtitle, summary, coverUrl, contentUrl,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        selectDatabase(type);
        essayMapper.essayUpdate(essayInfo);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 获取文章列表处理逻辑
     *
     * @return 文章列表
     */
    public JSONObject essayList(String type) throws DataFormatException {
        selectDatabase(type);
        List<EssayInfo> essayInfos = essayMapper.essayList();
        CustomerContextHolder.clearDataSource();
        JSONArray jsonArray = (JSONArray) JSON.toJSON(essayInfos);
        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        return result;
    }

    /**
     * 删除一篇文章处理逻辑
     *
     * @param essayId 用户自定义文章ID
     */
    public void essayDelete(String essayId, String type) throws DataFormatException {
        checkDataFormat(new String[]{essayId});
        selectDatabase(type);
        essayMapper.essayDelete(essayId);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 选择数据库
     *
     * @param type  环境类型 test或prod
     */
    private void selectDatabase(String type){
        switch (type) {
            case TEST:
                CustomerContextHolder.selectTestDataSource();
                break;
            case PROD:
                CustomerContextHolder.selectProdDataSource();
                break;
            default:
                break;
        }
    }

    /**
     * 检查传入参数得格式
     *
     * @param param 传入的参数
     */
    private void checkDataFormat(String[] param) throws DataFormatException {
        for (String s : param) {
            if (Strings.isNullOrEmpty(s)) {
                throw new DataFormatException();
            }
        }
    }


}
