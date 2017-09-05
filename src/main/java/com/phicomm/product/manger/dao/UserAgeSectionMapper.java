package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.statistic.AgeSectionRequestBean;
import com.phicomm.product.manger.model.statistic.UserAgeSectionBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * age & gender
 * Created by wei.yang on 2017/9/4.
 */
@Repository
public interface UserAgeSectionMapper {

    /**
     * 更新信息
     *
     * @param userAgeSectionBean 年龄段及数量
     */
    void updateAgeGenderInfo(@Param("userAgeSectionBean") UserAgeSectionBean userAgeSectionBean);

    /**
     * 查询请求
     *
     * @param requestBean 查询信息
     * @return 查询到的信息
     */
    UserAgeSectionBean obtainUserAgeSection(@Param("requestBean") AgeSectionRequestBean requestBean);
}
