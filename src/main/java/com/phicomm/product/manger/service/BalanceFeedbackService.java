package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.phicomm.product.manger.config.FeedbackPropertiesConfig;
import com.phicomm.product.manger.enumeration.RequestType;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import com.phicomm.product.manger.exception.*;
import com.phicomm.product.manger.model.feedback.*;
import com.phicomm.product.manger.model.permission.Permission;
import com.phicomm.product.manger.utils.ExceptionUtil;
import com.phicomm.product.manger.utils.FileUtil;
import com.phicomm.product.manger.utils.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 接收用户反馈信息
 *
 * @author qiang.ren
 * @date 2017/11/2
 */
@Service
public class BalanceFeedbackService {

    private static final Logger logger = Logger.getLogger(BalanceFeedbackService.class);

    private final String FEEDBACK_WITHOUT_FILTER_URL;

    private final String FEEDBACK_PAGE_WITHOUT_FILTER_URL;

    private final String FEEDBACK_PAGE_FILTER_URL;

    private final String FEEDBACK_REPLY;

    private final String FEEDBACK_DIALOG_CUSTOMER_REVOKE_URL;

    private final String FEEDBACK_STATIC;

    private final String FEEDBACK_TERMINAL_LIST;

    private final String FEEDBACK_LOCK;

    private static final int DATA_FORMAT_EXCEPTION = 2;

    private static final int FEEDBACK_EMPTY_EXCEPTION = 18;

    private static final int APP_TYPE_NOT_FOUND_EXCEPTION = 22;

    private static final int FEEDBACK_NOT_FOUND_EXCEPTION = 24;

    private static final int FEEDBACK_LOCK_EXCEPTION = 25;

    private static final int DIALOG_REVOKE_EXCEPTION = 26;

    @Autowired
    public BalanceFeedbackService(FeedbackPropertiesConfig propertiesConfig){
        String baseUrl = propertiesConfig.getBaseUrl();
        this.FEEDBACK_WITHOUT_FILTER_URL = baseUrl + "feedback/list";
        this.FEEDBACK_PAGE_WITHOUT_FILTER_URL = baseUrl + "feedback/page/list";
        this.FEEDBACK_PAGE_FILTER_URL = baseUrl + "feedback/page/list/filter";
        this.FEEDBACK_REPLY = baseUrl + "feedback/customer/reply";
        this.FEEDBACK_DIALOG_CUSTOMER_REVOKE_URL = baseUrl + "feedback/dialog/customer/revoke";
        this.FEEDBACK_STATIC = baseUrl + "feedback/statistic";
        this.FEEDBACK_TERMINAL_LIST = baseUrl + "feedback/terminal/list";
        this.FEEDBACK_LOCK = baseUrl + "feedback/lock";
        Assert.notNull(baseUrl);
        Assert.notNull(this.FEEDBACK_WITHOUT_FILTER_URL);
        Assert.notNull(this.FEEDBACK_PAGE_WITHOUT_FILTER_URL);
        Assert.notNull(this.FEEDBACK_PAGE_FILTER_URL);
        Assert.notNull(this.FEEDBACK_REPLY);
        Assert.notNull(this.FEEDBACK_DIALOG_CUSTOMER_REVOKE_URL);
        Assert.notNull(this.FEEDBACK_STATIC);
        Assert.notNull(this.FEEDBACK_TERMINAL_LIST);
        Assert.notNull(this.FEEDBACK_LOCK);
    }

    /**
     * 获取反馈列表（不带条件）
     *
     * @param maxIdBean maxId
     * @return 用户和反馈基本信息列表
     */
    public List<FeedbackWithUserInfo> fetchFeedbackWithoutFilter(MaxIdBean maxIdBean) {
        return getFeedbackData(maxIdBean, FEEDBACK_WITHOUT_FILTER_URL);
    }

    /**
     * 不加选择的获取反馈意见
     *
     * @param pageBean 页码
     * @return 反馈意见列表
     */
    public List<FeedbackWithUserInfo> fetchFeedbackWithoutFilter(PageBean pageBean) throws DataFormatException {
        return getFeedbackData(pageBean, FEEDBACK_PAGE_WITHOUT_FILTER_URL);
    }

    /**
     * 获取用户的权限列表
     *
     * @return 用户的权限列表
     */
    public List<Permission> getPermissionList() {
        List<Permission> permissions = Lists.newArrayList();
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attrs.getRequest().getSession();
        Set<String> permissionSet = (Set<String>) session.getAttribute(SessionKeyEnum.USER_PERMISSIONS.getKeyName());
        for (String permission:permissionSet) {
            permissions.add(new Permission(permission));
        }
        return permissions;
    }

    /**
     * 客服回复：
     * 1.核对参数格式；
     * 2.查看意见状态（是否已经有人锁定了，且未超过有效时间）
     * 3.更新意见状态
     * 4.写入新的对话
     *
     * @param appId      appId
     * @param sessionId  sessionId
     * @param dialogText dialogText
     * @throws FeedbackNotFoundException 反馈意见不存在
     * @throws FeedbackEmptyException    回复为空
     * @throws DataFormatException       数据格式异常
     * @throws FeedbackLockException     反馈意见锁定异常
     */
    public void customerReply(String appId, String sessionId, String dialogText, MultipartFile file1,
                              MultipartFile file2, MultipartFile file3, MultipartFile file4)
            throws FeedbackNotFoundException, FeedbackEmptyException, DataFormatException, FeedbackLockException {
        MultipartFile[] files = new MultipartFile[]{file1, file2, file3, file4};
        List<String> dialogPictures = uploadFile(files);
        String userId = HttpUtil.getCurrentUserInfo().getId().toString();
        ReplyBean replyBean = new ReplyBean(appId, sessionId, userId, dialogText, dialogPictures);
        logger.info(replyBean.toString());
        int exceptionNum;
        String result;
        try {
            result = HttpUtil.openPostRequest(FEEDBACK_REPLY, RequestType.POST.getKeyName(), (JSONObject) JSONObject.toJSON(replyBean));
            exceptionNum = Integer.parseInt(JSONObject.parseObject(result).get("status").toString());
            if (DATA_FORMAT_EXCEPTION == exceptionNum) {
                throw new DataFormatException();
            } else if (FEEDBACK_EMPTY_EXCEPTION == exceptionNum) {
                throw new FeedbackEmptyException();
            } else if (FEEDBACK_NOT_FOUND_EXCEPTION == exceptionNum) {
                throw new FeedbackNotFoundException();
            } else if (FEEDBACK_LOCK_EXCEPTION == exceptionNum) {
                throw new FeedbackLockException();
            }
        } catch (IOException | FirmwareTriggerFailException e) {
            ExceptionUtil.getErrorMessage(e);
        }
    }

    /**
     * 上传文件
     *
     * @param files 文件列表
     * @return url
     */
    private List<String> uploadFile(MultipartFile[] files) {
        List<String> result = Lists.newArrayList();
        for (MultipartFile file : files) {
            if (file.getSize() != 0) {
                try {
                    result.add(FileUtil.uploadFileToHermes(file).get("url"));
                } catch (UploadFileException e) {
                    ExceptionUtil.getErrorMessage(e);
                }
            }
        }
        return result;
    }

    /**
     * 后台智能删除用户未读的消息
     *
     * @param revokeDialogBean 要删除的数据
     * @throws DataFormatException   数据格式异常
     * @throws DialogRevokeException 数据已处理，无法删除
     */
    public void customerRevoker(RevokeDialogBean revokeDialogBean) throws DataFormatException, DialogRevokeException, FeedbackLockException {
        revokeDialogBean.setCustomerId(HttpUtil.getCurrentUserInfo().getId().toString());
        logger.info(revokeDialogBean.toString());
        int exceptionNum = 0;
        String result = null;
        try {
            result = HttpUtil.openPostRequest(FEEDBACK_DIALOG_CUSTOMER_REVOKE_URL, RequestType.POST.getKeyName(), (JSONObject) JSONObject.toJSON(revokeDialogBean));
        } catch (IOException | FirmwareTriggerFailException e) {
            ExceptionUtil.getErrorMessage(e);
        }
        if (!Strings.isNullOrEmpty(result)) {
            exceptionNum = Integer.parseInt(JSONObject.parseObject(result).get("status").toString());
            logger.info(exceptionNum);
        }
        if (DATA_FORMAT_EXCEPTION == exceptionNum) {
            throw new DataFormatException();
        } else if (FEEDBACK_LOCK_EXCEPTION == exceptionNum) {
            throw new FeedbackLockException();
        } else if (DIALOG_REVOKE_EXCEPTION == exceptionNum) {
            throw new DialogRevokeException();
        }
    }

    /**
     * 获取统计数据
     *
     * @return 统计数据
     */
    public NewFeedbackCountBean feedbackStatistic() {
        String result = null;
        try {
            result = HttpUtil.openPostRequest(FEEDBACK_STATIC, RequestType.POST.getKeyName(), new JSONObject());
        } catch (IOException | FirmwareTriggerFailException e) {
            ExceptionUtil.getErrorMessage(e);
        }
        if (!Strings.isNullOrEmpty(result)) {
            String data = JSONObject.toJSONString(JSONObject.parseObject(result).get("data"));
            logger.info(data);
            return JSONObject.parseObject(data, NewFeedbackCountBean.class);
        }
        return null;
    }

    /**
     * 获取设备关系数据
     *
     * @param appIdBean appId
     * @return 需要的设备关系
     * @throws DataFormatException      数据格式异常
     * @throws AppTypeNotFoundException 类型错误
     */
    public Map<String, List<TerminalBean>> obtainTerminalInfo(AppIdBean appIdBean)
            throws DataFormatException, AppTypeNotFoundException {
        logger.info(appIdBean);
        String result = null;
        int exceptionNum;
        try {
            result = HttpUtil.openPostRequest(FEEDBACK_TERMINAL_LIST, RequestType.POST.getKeyName(), (JSONObject) JSONObject.toJSON(appIdBean));
        } catch (IOException | FirmwareTriggerFailException e) {
            ExceptionUtil.getErrorMessage(e);
        }
        if (!Strings.isNullOrEmpty(result)) {
            exceptionNum = Integer.parseInt(JSONObject.parseObject(result).get("status").toString());
            if (DATA_FORMAT_EXCEPTION == exceptionNum) {
                throw new DataFormatException();
            } else if (APP_TYPE_NOT_FOUND_EXCEPTION == exceptionNum) {
                throw new AppTypeNotFoundException();
            }
            JSONObject data = (JSONObject) JSONObject.parseObject(result).get("data");
            Map<String, List<TerminalBean>> map = getTerminalInfo(data);
            logger.info(map.toString());
            return map;
        }
        return null;
    }

    /**
     * 获取筛选数据
     *
     * @param historyRequestBean 请求条件
     * @return 反馈意见
     * @throws DataFormatException 数据格式异常
     */
    public FilterWithTotalCountBean fetchFeedbackList(HistoryRequestWithPage historyRequestBean) throws DataFormatException {
        logger.info(historyRequestBean);
        String result = null;
        int exceptionNum;
        try {
            result = HttpUtil.openPostRequest(FEEDBACK_PAGE_FILTER_URL, RequestType.POST.getKeyName(), (JSONObject) JSONObject.toJSON(historyRequestBean));
        } catch (IOException | FirmwareTriggerFailException e) {
            ExceptionUtil.getErrorMessage(e);
        }
        if (!Strings.isNullOrEmpty(result)) {
            exceptionNum = Integer.parseInt(JSONObject.parseObject(result).get("status").toString());
            if (DATA_FORMAT_EXCEPTION == exceptionNum) {
                throw new DataFormatException();
            }
            String data = JSONObject.toJSONString(JSONObject.parseObject(result).get("data"));
            logger.info(data);
            FilterWithTotalCountBean filterWithTotalCountBean = JSONObject.parseObject(data, FilterWithTotalCountBean.class);
            logger.info(filterWithTotalCountBean);
            return filterWithTotalCountBean;
        }
        return null;
    }

    /**
     * 锁定反馈意见
     *
     * @param requestBean 请求数据
     * @throws DataFormatException       数据格式异常
     * @throws FeedbackNotFoundException 反馈意见不存在
     * @throws FeedbackLockException     锁定异常
     */
    public void lockFeedback(LockRequestBean requestBean)
            throws DataFormatException, FeedbackNotFoundException, FeedbackLockException {
        requestBean.setUserId(HttpUtil.getCurrentUserInfo().getId().toString());
        logger.info(requestBean);
        String result = null;
        int exceptionNum = 0;
        try {
            result = HttpUtil.openPostRequest(FEEDBACK_LOCK, RequestType.POST.getKeyName(), (JSONObject) JSONObject.toJSON(requestBean));
        } catch (IOException | FirmwareTriggerFailException e) {
            ExceptionUtil.getErrorMessage(e);
        }
        if (!Strings.isNullOrEmpty(result)) {
            exceptionNum = Integer.parseInt(JSONObject.parseObject(result).get("status").toString());
            logger.info(exceptionNum);
        }
        if (DATA_FORMAT_EXCEPTION == exceptionNum) {
            throw new DataFormatException();
        } else if (FEEDBACK_NOT_FOUND_EXCEPTION == exceptionNum) {
            throw new FeedbackNotFoundException();
        } else if (FEEDBACK_LOCK_EXCEPTION == exceptionNum) {
            throw new FeedbackLockException();
        }
    }

    /**
     * 通过网络获取反馈数据
     *
     * @return 反馈数据列表
     */
    private List<FeedbackWithUserInfo> getFeedbackData(Object obj, String url) {
        logger.info(obj);
        String result = null;
        try {
            result = HttpUtil.openPostRequest(url, RequestType.POST.getKeyName(), (JSONObject) JSONObject.toJSON(obj));
        } catch (IOException | FirmwareTriggerFailException e) {
            ExceptionUtil.getErrorMessage(e);
        }
        if (!Strings.isNullOrEmpty(result)) {
            String data = JSONObject.toJSONString(JSONObject.parseObject(result).get("data"));
            logger.info(data);
            List<FeedbackWithUserInfo> feedbackWithUserInfos = JSON.parseArray(data, FeedbackWithUserInfo.class);
            logger.info(feedbackWithUserInfos);
            return feedbackWithUserInfos;
        }
        return null;
    }

    /**
     * 获取设备关系数据
     *
     * @param jsonObject json对象
     * @return 设备关系数据
     */
    private Map<String, List<TerminalBean>> getTerminalInfo(JSONObject jsonObject) {
        Map<String, List<TerminalBean>> map = Maps.newHashMap();
        Set<String> set = jsonObject.keySet();
        for (String key : set) {
            List<TerminalBean> value = JSON.parseArray(JSONObject.toJSONString(jsonObject.get(key)), TerminalBean.class);
            map.put(key, value);
        }
        return map;
    }

}
