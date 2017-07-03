package com.phicomm.product.manger.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 电子秤成员信息
 * Created by wei.yang on 2017/7/3.
 */
@Repository
public interface BalanceUserManagerMapper {

    /**
     * 根据mac地址查找该mac地址所绑定的所有成员数量
     *
     * @param mac mac地址
     * @return 绑定该电子秤的成员数
     */
    int obtainMemberCount(@Param("mac") String mac);
}
