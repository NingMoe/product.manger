package com.phicomm.product.manger.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by xiang.zhang on 2017/9/15.
 */
@Repository
public interface PictureUploadParamConfigMapper {
    /**
     * 获取配置信息
     */
    String getPictureConfig();

    /**
     * 清除
     */
   void clean();
    /**
     * 插入
     */
    void insert(@Param("param") String param) ;


}
