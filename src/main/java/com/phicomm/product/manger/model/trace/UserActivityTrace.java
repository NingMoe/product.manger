package com.phicomm.product.manger.model.trace;

import java.util.List;

/**
 * 用户活跃度模型
 *
 * @author qiang.ren
 * @date 2017/12/29
 */
public class UserActivityTrace {

    private List<int[]> data;

    public List<int[]> getData() {
        return data;
    }

    public void setData(List<int[]> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserActivityTrace{" +
                "data=" + data +
                '}';
    }
}
