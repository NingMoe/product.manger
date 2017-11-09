package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/10/31
 */
public class FeedbackLockStatusBean {

    private boolean locking;

    private String lockUserId;

    private String dialogStatus;

    public boolean isLocking() {
        return locking;
    }

    public FeedbackLockStatusBean setLocking(boolean locking) {
        this.locking = locking;
        return this;
    }

    public String getLockUserId() {
        return lockUserId;
    }

    public FeedbackLockStatusBean setLockUserId(String lockUserId) {
        this.lockUserId = lockUserId;
        return this;
    }

    public String getDialogStatus() {
        return dialogStatus;
    }

    public FeedbackLockStatusBean setDialogStatus(String dialogStatus) {
        this.dialogStatus = dialogStatus;
        return this;
    }

    @Override
    public String toString() {
        return "FeedbackLockStatusBean{" +
                "locking=" + locking +
                ", lockUserId='" + lockUserId + '\'' +
                ", dialogStatus='" + dialogStatus + '\'' +
                '}';
    }
}
