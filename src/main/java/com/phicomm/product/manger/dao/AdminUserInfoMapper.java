package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.user.AdminUserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserInfoMapper {

    /**
     * 获取用户的信息
     *
     * @param phoneNumber 手机号
     * @return 用户信息
     */
    AdminUserInfo getUserInfo(@Param("phoneNumber") String phoneNumber);

    /**
     * 插入数据
     */
    void insert(@Param("phoneNumber") String phoneNumber,
                @Param("email") String email,
                @Param("username") String username,
                @Param("sex") int sex,
                @Param("role") String role,
                @Param("headPicture") String headPicture);

    /**
     * 获得用户嘻嘻你列表
     */
    List<AdminUserInfo> getUserInfoList();

    /**
     * 删除该用户
     *
     * @param phoneNumber 手机号
     */
    void delete(@Param("phoneNumber") String phoneNumber);

    /**
     * 更新用户信息
     *
     * @param adminUserInfo 用户信息
     */
    void update(@Param("adminUserInfo") AdminUserInfo adminUserInfo);
}