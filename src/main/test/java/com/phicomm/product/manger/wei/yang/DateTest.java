package com.phicomm.product.manger.wei.yang;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.product.manger.model.table.FeedbackRequestBean;
import org.joda.time.DateTime;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * date
 * Created by wei.yang on 2017/7/7.
 */
public class DateTest {

    @Test
    public void test(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        System.out.println(dateFormat.format(new Date(0)));
        System.out.println(dateFormat.format(new DateTime().minusDays(2).toDate()));
        System.out.println(dateFormat.format(new DateTime().toDate()));
        FeedbackRequestBean requestBean=new FeedbackRequestBean();
        requestBean.setPageSize(1);
        requestBean.setAppType("2");
        requestBean.setStartId(23);
        JSONObject jsonObject= (JSONObject) JSONObject.toJSON(requestBean);
        System.out.println(jsonObject);
    }
}
