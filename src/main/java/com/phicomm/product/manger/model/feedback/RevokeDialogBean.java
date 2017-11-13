package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/11/7
 */
public class RevokeDialogBean {

    private String customerId;

    private String sessionId;

    private Long dialogId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
    }

    @Override
    public String toString() {
        return "RevokeDialogBean{" +
                "customerId='" + customerId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", dialogId=" + dialogId +
                '}';
    }
}
