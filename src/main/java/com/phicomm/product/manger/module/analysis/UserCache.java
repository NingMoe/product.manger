package com.phicomm.product.manger.module.analysis;

import com.phicomm.product.manger.model.statistic.AnalysisUserMember;
import org.springframework.stereotype.Service;

/**
 * 缓存接口
 * Created by Qiang on 2017/7/19.
 */
public interface UserCache {

    /**
     * 获取用户和成员的数据缓存
     *
     * @return 用户和成员的数据
     */
    AnalysisUserMember getUserCache();

}
