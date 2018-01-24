package com.phicomm.product.manger.dao;



import com.phicomm.product.manger.model.watchplate.WatchPlatePictureUpload;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by xiang.zhang on 2017/9/7.
 * @author xiang.zhang
 */
@Repository
public interface WatchPlatePictureUploadMapper {

    /**
     * 添加图片记录
     * @param watchPlatePictureUpload   图片对象
     */
    void watchPlatePictureUpload(@Param("watchPlatePictureUpload") WatchPlatePictureUpload watchPlatePictureUpload);


    /**
     * 删除图片信息
     * @param data 图片信息
     */
     void watchPlatePictureDelete(@Param("data") List<WatchPlatePictureUpload> data);

    /**
     * 获取表盘列表
     */
     List<WatchPlatePictureUpload> watchPlatePictureList();

}
