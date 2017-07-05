package com.phicomm.product.manger.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPermissionInfoMapper {

    /**
     * 获得用户的授权列表
     *
     * @param phoneNumber 手机号
     * @return 授权列表
     */
    List<String> getUserPermission(@Param("phoneNumber") String phoneNumber);

}