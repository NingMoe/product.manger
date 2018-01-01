package com.phicomm.product.manger.model.terminal;

/**
 * @author wei.yang on 2018/1/1
 */
public class SearchWithPageSizeEntity extends BaseSearchEntity{

    private int pageSize;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "SearchWithPageSizeEntity{" +
                "pageSize=" + pageSize +
                '}';
    }
}
