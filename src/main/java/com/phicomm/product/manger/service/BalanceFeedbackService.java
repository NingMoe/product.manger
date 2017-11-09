package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.phicomm.product.manger.enumeration.RequestType;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.FeedbackNotFoundException;
import com.phicomm.product.manger.exception.FirmwareTriggerFailException;
import com.phicomm.product.manger.model.feedback.*;
import com.phicomm.product.manger.utils.ExceptionUtil;
import com.phicomm.product.manger.utils.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 接收用户反馈信息
 *
 * @author qiang.ren
 * @date 2017/11/2
 */
@Service
public class BalanceFeedbackService {

    private static final Logger logger = Logger.getLogger(BalanceFeedbackService.class);

    private static final String BASE_URL = "http://114.141.173.27:16816/phicomm-account/";

    private static final String FEEDBACK_WITHOUT_FILTER_URL = BASE_URL + "feedback/list";

    /**
     * 获取反馈列表(带条件)
     *
     * @param historyRequestBean 历史反馈
     * @return 用户和反馈基本信息列表
     * @throws DataFormatException 数据格式异常
     */
    public List<FeedbackWithUserInfo> fetchFeedbackList(HistoryRequestBean historyRequestBean) throws DataFormatException {
        return null;
    }

    /**
     * 获取反馈列表（不带条件）
     *
     * @param maxIdBean maxId
     * @return 用户和反馈基本信息列表
     */
    public List<FeedbackWithUserInfo> fetchFeedbackWithoutFilter(MaxIdBean maxIdBean) {
        logger.info(maxIdBean);
        String result = null;
        try {
            result = HttpUtil.openPostRequest(FEEDBACK_WITHOUT_FILTER_URL, RequestType.POST.getKeyName(), (JSONObject) JSONObject.toJSON(maxIdBean));
        } catch (IOException | FirmwareTriggerFailException e) {
            ExceptionUtil.getErrorMessage(e);
        }
        if (!Strings.isNullOrEmpty(result)){
            String data = JSONObject.toJSONString(JSONObject.parseObject(result).get("data"));
            logger.info(data);
            List<FeedbackWithUserInfo> feedbackWithUserInfos = JSON.parseArray(data, FeedbackWithUserInfo.class);
            logger.info(feedbackWithUserInfos);
            return feedbackWithUserInfos;
        }
        return null;
    }

    /**
     * 客服去获取某个问题的锁定状态
     *
     * @param requestBean 请求
     * @return 反馈锁定状态
     * @throws DataFormatException       数据格式异常
     * @throws FeedbackNotFoundException 反馈没找到异常
     */
    public FeedbackLockStatusBean obtainFeedbackStatus(FeedbackStatusRequestBean requestBean)
            throws DataFormatException, FeedbackNotFoundException {
        return null;
    }
}
