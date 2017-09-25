package com.phicomm.product.manger.dao;


import com.phicomm.product.manger.model.picture.PictureUpload;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by xiang.zhang on 2017/9/7.
 */
@Repository
public interface PictureMapper {

    /**
     * 添加图片记录
     *
     * @param pictureUpload   图片对象
     *
     */
    void pictureUpload(@Param("pictureUpload") PictureUpload pictureUpload);


    /**
     * 重新上传覆盖版本下所有图片信息
     * @param picVerison
     */
     void pictureDelete(@Param("picVerison") String picVerison);

}
