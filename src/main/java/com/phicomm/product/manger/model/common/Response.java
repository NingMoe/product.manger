package com.phicomm.product.manger.model.common;

/**
 * 响应
 * Created by yufei.liu on 2017/2/24.
 */
public class Response<T> {

    private int status;

    private String description;

    private T data;

    public int getStatus() {
        return status;
    }

    public Response<T> setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

}
