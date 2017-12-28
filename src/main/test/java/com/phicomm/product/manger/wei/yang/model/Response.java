package com.phicomm.product.manger.wei.yang.model;

/**
 * test
 * Created by wei.yang on 2017/8/11.
 */
class Response<T> {

    private int code;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
