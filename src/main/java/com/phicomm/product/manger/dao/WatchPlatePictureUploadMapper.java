package com.phicomm.product.manger.dao;



import com.phicomm.product.manger.model.watchplate.WatchPlatePictureUpload;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by xiang.zhang on 2017/9/7.
 */
@Repository
public interface WatchPlatePictureUploadMapper {

    /**
     * 添加图片记录
     * @param watchPlatePictureUpload   图片对象
     */
    void watchPlatePictureUpload(@Param("watchPlatePictureUpload") WatchPlatePictureUpload watchPlatePictureUpload);


    /**
     * 重新上传覆盖版本下所有图片信息
     * @param picVerison
     */
     void watchPlatePictureDelete(@Param("picVerison") String picVerison);

    /**
     * 获取表盘图片列表
     * @return
     */
     List<WatchPlatePictureUpload> watchPlatePictureList();

}
