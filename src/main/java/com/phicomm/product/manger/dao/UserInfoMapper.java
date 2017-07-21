package com.phicomm.product.manger.dao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 电子秤用户信息
 * Created by qiang.ren on 2017/7/18.
 */
@Repository
public interface UserInfoMapper {

    /**
     * 按年龄统计所有用户
     *
     * @param lowAge 年龄段下限
     * @param highAge 年龄段上限
     * @param gender 性别
     * @return 性别和年龄都匹配的用户数量
     */
    int statisticUserByAge(@Param("lowAge")int lowAge, @Param("highAge")int highAge,  @Param("gender")int gender);

    /**
     * 统计所有用户
     *
     * @param gender 性别
     * @return 性别匹配的用户数量
     */
    int statisticUser(@Param("gender") int gender);

}