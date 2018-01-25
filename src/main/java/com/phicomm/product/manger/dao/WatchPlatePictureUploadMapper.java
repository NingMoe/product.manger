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
     * 图片版本查找所有图片信息
     * @param picOldVersion 旧的版本号
     * @return 返回所有图片信息
     */
     List<WatchPlatePictureUpload> watchPlatePictureFind(@Param("picOldVersion") String picOldVersion);


    /**
     * 获取表盘列表
     * @return
     */
    List<WatchPlatePictureUpload> watchPlatePictureList();

}
