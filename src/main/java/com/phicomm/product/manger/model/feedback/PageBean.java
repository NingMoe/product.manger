package com.phicomm.product.manger.model.feedback;

/**
 * @author wei.yang on 2017/11/8
 */
public class PageBean {

    private Integer page;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "page=" + page +
                '}';
    }
}
