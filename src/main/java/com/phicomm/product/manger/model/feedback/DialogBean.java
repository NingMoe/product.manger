package com.phicomm.product.manger.model.feedback;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * 会话记录
 *
 * @author wei.yang on 2017/10/30
 */
public class DialogBean {

    private Long dialogId;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    private Date createTime;

    private String dialogType;

    private String dialogText;

    private String dialogPicture;

    private List dialogPictures;

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDialogType() {
        return dialogType;
    }

    public void setDialogType(String dialogType) {
        this.dialogType = dialogType;
    }

    public String getDialogText() {
        return dialogText;
    }

    public void setDialogText(String dialogText) {
        this.dialogText = dialogText;
    }

    public String getDialogPicture() {
        return dialogPicture;
    }

    public void setDialogPicture(String dialogPicture) {
        this.dialogPicture = dialogPicture;
    }

    public List getDialogPictures() {
        return dialogPictures;
    }

    public void setDialogPictures(List dialogPictures) {
        this.dialogPictures = dialogPictures;
    }

    @Override
    public String toString() {
        return "DialogBean{" +
                "dialogId=" + dialogId +
                ", createTime=" + createTime +
                ", dialogType='" + dialogType + '\'' +
                ", dialogText='" + dialogText + '\'' +
                ", dialogPicture='" + dialogPicture + '\'' +
                ", dialogPictures=" + dialogPictures +
                '}';
    }
}
