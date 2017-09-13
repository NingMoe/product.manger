package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.statistic.UserAgeSectionOriginalBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 按年龄统计所有成员
     *
     * @param lowAge 年龄段下限
     * @param highAge 年龄段上限
     * @param sex 性别
     * @return 性别和年龄都匹配的成员数量
     */
    int statisticMemberByAge(@Param("lowAge") int lowAge, @Param("highAge") int highAge, @Param("sex") int sex);

    /**
     * 统计所有成员
     *
     * @param sex 性别
     * @return 性别匹配的成员数量
     */
    int statisticMember(@Param("sex") int sex);

    /**
     * 获取用户性别与年龄段信息
     *
     * @param gender 性别
     * @return 年龄段信息
     */
    List<UserAgeSectionOriginalBean> obtainUserGenderInfo(@Param("gender") int gender);
}
