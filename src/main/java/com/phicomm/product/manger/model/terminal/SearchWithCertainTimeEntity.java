package com.phicomm.product.manger.model.terminal;

/**
 * @author wei.yang on 2018/1/1
 */
public class SearchWithCertainTimeEntity extends BaseSearchEntity {

    /**
     * 只有在存在确定的查询时间时才可以赋值，否则置为空，作为总量查询
     */
    private String searchTime;

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    @Override
    public String toString() {
        return "SearchWithCertainTimeEntity{" +
                "searchTime='" + searchTime + '\'' +
                '}';
    }
}
