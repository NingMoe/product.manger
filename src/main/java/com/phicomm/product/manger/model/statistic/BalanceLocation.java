package com.phicomm.product.manger.model.statistic;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 省份和激活次数
 * <p>
 * Created by yufei.liu on 2017/6/25.
 */
public class BalanceLocation {

    /**
     * 省份
     */
    private static final Set<String> PROVINCES = Sets.newHashSet(
            "北京", "天津", "上海", "重庆", "河北", "山西", "辽宁", "吉林",
            "黑龙", "江苏", "浙江", "安徽", "福建", "台湾", "江西", "山东",
            "河南", "湖北", "湖南", "广东", "海南", "四川", "贵州", "云南",
            "陕西", "甘肃", "青海", "内蒙", "广西", "西藏", "宁夏", "新疆",
            "香港", "澳门"
    );

    private String province;

    private Integer number;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BalanceLocation format() {
        BalanceLocation balanceLocation = new BalanceLocation();
        if (Strings.isNullOrEmpty(this.province)) {
            return null;
        }
        String key = this.province.substring(0, 2);
        if (!PROVINCES.contains(key)) {
            return null;
        }
        if ("黑龙".equals(key)) {
            balanceLocation.setProvince("黑龙江");
        } else {
            balanceLocation.setProvince(key);
        }
        balanceLocation.setNumber(this.number);
        return balanceLocation;
    }

    @Override
    public String toString() {
        return "BalanceLocationStatistic{" +
                "province='" + province + '\'' +
                ", number=" + number +
                '}';
    }

}
