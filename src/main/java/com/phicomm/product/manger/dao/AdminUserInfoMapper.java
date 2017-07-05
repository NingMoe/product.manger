package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.table.AdminUserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserInfoMapper {

    /**
     * 获取用户的信息
     *
     * @param phoneNumber 手机号
     * @return 用户信息
     */
    AdminUserInfo getUserInfo(@Param("phoneNumber") String phoneNumber);

}