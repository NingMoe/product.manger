package com.phicomm.product.manger.model.feedback;

/**
 * 用户评估
 *
 * @author wei.yang on 2017/10/30
 */
public class EvaluateBean {

    private Long dialogId;

    private String sessionId;

    private Integer evaluateStar;

    private String evaluationText;

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getEvaluateStar() {
        return evaluateStar;
    }

    public void setEvaluateStar(Integer evaluateStar) {
        this.evaluateStar = evaluateStar;
    }

    public String getEvaluationText() {
        return evaluationText;
    }

    public void setEvaluationText(String evaluationText) {
        this.evaluationText = evaluationText;
    }

    @Override
    public String toString() {
        return "EvaluateBean{" +
                "dialogId=" + dialogId +
                ", sessionId='" + sessionId + '\'' +
                ", evaluateStar='" + evaluateStar + '\'' +
                ", evaluationText='" + evaluationText + '\'' +
                '}';
    }
}
