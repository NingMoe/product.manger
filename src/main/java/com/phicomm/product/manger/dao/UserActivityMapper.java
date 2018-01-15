package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.trace.UserActivityInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户活跃度数据表操作
 *
 * @author qiang.ren
 * @date 2018/1/10
 */
@Repository
public interface UserActivityMapper {

    /**
     * 添加用户活跃度记录
     *
     * @param userActivityInfo 用户活跃度类
     */
    void addUserActivity(@Param("userActivityInfo") UserActivityInfo userActivityInfo);

    /**
     * 更新用户活跃度记录
     *
     * @param userActivityInfo 用户活跃度类
     */
    void updateUserActivity(@Param("userActivityInfo") UserActivityInfo userActivityInfo);

    /**
     * 获取用户活跃度
     *
     * @param date  日期
     * @param type  PV或者UV
     * @param appId appId
     * @return UserActivityInfo
     */
    UserActivityInfo getUserActivity(@Param("date") String date, @Param("type") String type,
                                     @Param("appId") String appId);

    /**
     * 是否已经存在用户活跃度
     *
     * @param date  日期
     * @param type  PV或者UV
     * @param appId appId
     * @return int
     */
    int isExistUserActivity(@Param("date") String date, @Param("type") String type,
                            @Param("appId") String appId);
}
