package com.phicomm.product.manger.service;

import com.google.common.collect.Lists;
import com.phicomm.product.manger.model.trace.UserActivityInputInfo;
import com.phicomm.product.manger.model.trace.UserActivityTrace;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户活跃度处理逻辑
 *
 * @author qiang.ren
 * @date 2017/12/29
 */
@Service
public class UserActivityService {

    private static final int[] yestday = new int[]{1295,429,196,162,270,1264,4280,5717,5192,4747,4877,5076,5294,4957,4582,4883,5422,6745,8710,9888,10460,10287,7172,3261};
    private static final int[] today = new int[]{1127,419,198,168,280,1121,4055,5571,4746,4702,4887,5231,5394,5169,4876,1320,0,0,0,0,0,0,0,0};

    /**
     * 统计24小时用户活跃度（PV）
     * @param userActivityInputInfo 用户活跃度传入信息
     * @return 24小时用户活跃度
     */
    public UserActivityTrace traceUserActivityPV(UserActivityInputInfo userActivityInputInfo){
        if (null == userActivityInputInfo){
            System.out.println("传入为空------------------");
        }
        UserActivityTrace userActivityTrace = new UserActivityTrace();
        List<int[]> list = Lists.newArrayList();
        list.add(today);
        list.add(yestday);
        userActivityTrace.setData(list);
        return userActivityTrace;
    }

    /**
     * 统计24小时用户活跃度（UV）
     * @param userActivityInputInfo 用户活跃度传入信息
     * @return 24小时用户活跃度
     */
    public UserActivityTrace traceUserActivityUV(UserActivityInputInfo userActivityInputInfo){
        if (null == userActivityInputInfo){
            System.out.println("传入为空------------------");
        }
        UserActivityTrace userActivityTrace = new UserActivityTrace();
        List<int[]> list = Lists.newArrayList();
        list.add(today);
        list.add(yestday);
        userActivityTrace.setData(list);
        return userActivityTrace;
    }
}
