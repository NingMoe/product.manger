package com.phicomm.product.manger.dao;


import com.phicomm.product.manger.model.picture.PictureUpload;
import jdk.nashorn.internal.objects.annotations.Where;
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
     * 获取图片记录
     * @return
     */
    List<PictureUpload> pictureList( @Param("picVersion") String picVersion);


    /**
     * 更新使用状态 “0”和“1”
     * @param pictureUpload
     */
    void pictureUpdate(@Param("pictureUpload") PictureUpload pictureUpload);

    /**
     * 覆盖版本下所有图片信息
     * @param picVerison
     */
     void pictureDelete(@Param("picVerison") String picVerison);

}
