package com.phicomm.product.manger.model.feedback;

import java.util.List;

/**
 * 客服回复
 *
 * @author wei.yang on 2017/10/30
 */
public class ReplyBean {

    private String appId;

    private String sessionId;

    private String userId;

    private String dialogText;

    private List<String> dialogPictures;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDialogText() {
        return dialogText;
    }

    public void setDialogText(String dialogText) {
        this.dialogText = dialogText;
    }

    public List<String> getDialogPictures() {
        return dialogPictures;
    }

    public void setDialogPictures(List<String> dialogPictures) {
        this.dialogPictures = dialogPictures;
    }

    public ReplyBean(String appId, String sessionId, String userId, String dialogText, List<String> dialogPictures) {
        this.appId = appId;
        this.sessionId = sessionId;
        this.userId = userId;
        this.dialogText = dialogText;
        this.dialogPictures = dialogPictures;
    }

    @Override
    public String toString() {
        return "ReplyBean{" +
                "appId='" + appId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", userId='" + userId + '\'' +
                ", dialogText='" + dialogText + '\'' +
                ", dialogPictures=" + dialogPictures +
                '}';
    }
}
