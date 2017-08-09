package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.essay.EssayInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章表相关操作
 * Created by Qiang on 2017/8/7.
 */
@Repository
public interface EssayMapper {

    /**
     * 添加一条文章记录
     *
     * @param essayInfo   文章对象
     *
     */
    void essayUpload(@Param("essayInfo") EssayInfo essayInfo);

    /**
     * 更新一条文章记录
     *
     * @param essayInfo   文章对象
     *
     */
    void essayUpdate(@Param("essayInfo") EssayInfo essayInfo);

    /**
     * 判断是否已经存在该ID的文章
     *
     * @param essayId   用户自定义文章ID
     */
    boolean existEssay(@Param("essayId") String essayId);

    /**
     * 获取文章列表
     *
     * @return 文章列表
     */
    List<EssayInfo> essayList();

    /**
     * 删除一篇文章记录
     *
     * @param essayId  用户自定义文章ID
     *
     */
    void essayDelete(@Param("essayId") String essayId);

}
