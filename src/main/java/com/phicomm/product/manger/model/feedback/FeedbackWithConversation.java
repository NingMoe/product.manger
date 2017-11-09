package com.phicomm.product.manger.model.feedback;

import java.util.List;

/**
 * 反馈意见
 *
 * @author wei.yang on 2017/10/30
 */
public class FeedbackWithConversation extends FeedbackStatusBean {

    private List<DialogBean> dialogBeans;

    public List<DialogBean> getDialogBeans() {
        return dialogBeans;
    }

    public FeedbackWithConversation setDialogBeans(List<DialogBean> dialogBeans) {
        this.dialogBeans = dialogBeans;
        return this;
    }

    @Override
    public String toString() {
        return "FeedbackWithConversation{" +
                "dialogBeans=" + dialogBeans +
                '}';
    }
}
