package com.phicomm.product.manger.wei.yang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.phicomm.product.manger.model.statistic.BalanceLocationBean;
import com.phicomm.product.manger.enumeration.GenderEnum;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.util.*;

/**
 * test
 * Created by wei.yang on 2017/7/4.
 */
public class StringTest {

    @Test
    public void test(){
        System.out.println("5c:cf:7f:41:77:cf".length());
        System.out.println("A020A6351AD5".length());
        System.out.println("5ccf".substring(2,4));
    }

    @Test
    public void test1(){
        System.out.println(formatMac("A020A6351AD5"));
    }

    private String formatMac(String mac){
        int len=mac.length();
        StringBuilder builder=new StringBuilder();
        if (mac.length()==17){
            return mac.toLowerCase();
        }else {
            for (int i=0;i<len/2;i++){
                builder.append(mac.subSequence(2*i,2*i+2));
                if (!(i==len/2-1)){
                    builder.append(":");
                }
            }
            return builder.toString().toLowerCase();
        }
    }

    @Test
    public void test2(){
        List<BalanceLocationBean> balanceLocationBeans=new ArrayList<>();
        for (int i=0;i<10;i++){
            BalanceLocationBean locationBean=new BalanceLocationBean();
            locationBean.setActiveCity("市");
            locationBean.setActiveCountry("国家");
            locationBean.setActiveCounty("县");
            locationBean.setActiveProvince("省");
            balanceLocationBeans.add(locationBean);
        }
        JSONArray result= (JSONArray) JSON.toJSON(balanceLocationBeans);
        System.out.println(result);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",result);
        System.out.println(jsonObject);

    }

    private String formatLocation(BalanceLocationBean locationBean) {
        if (locationBean == null) {
            return "未知地点";
        }
        StringBuilder builder = new StringBuilder();
        String country = locationBean.getActiveCountry();
        String province = locationBean.getActiveProvince();
        String city = locationBean.getActiveCity();
        String county = locationBean.getActiveCounty();
        if (!Strings.isNullOrEmpty(country)) {
            builder.append(country);
            if (!Strings.isNullOrEmpty(province)) {
                builder.append("、").append(province);
                if (!Strings.isNullOrEmpty(city)) {
                    builder.append("、").append(city);
                    if (!Strings.isNullOrEmpty(county)) {
                        builder.append("、").append(county);
                    }
                }
            }
        } else {
            return "未知地点";
        }
        return builder.toString();
    }

    @Test
    public void test4(){
        String a="a";
        System.out.println(String.format("%32s",a).replaceAll(" ","0"));
        Map<Integer,Integer> map=new HashMap<>();
        map.put(1,2);
        System.out.println(map.get(2));
        System.out.println(GenderEnum.BOY.name());
        Date date=new Date();
        System.out.println(LocalDateTime.fromDateFields(date).minusDays(1).getDayOfYear()<LocalDateTime.fromDateFields(date).getDayOfYear());
        System.out.println(LocalDate.fromDateFields(date).minusDays(1));
        System.out.println(LocalDate.fromDateFields(date).minusDays(1).isBefore(LocalDate.now()));
    }
}
