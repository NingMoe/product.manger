package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.swagger.SwaggerAccountBean;
import com.phicomm.product.manger.model.swagger.SwaggerCoreMessageBean;
import com.phicomm.product.manger.model.swagger.SwaggerTestEnvironmentBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * swagger 账户管理
 * Created by wei.yang on 2017/7/19.
 */
@Repository
public interface SwaggerManagerMapper {

    /**
     * 获取全部列表
     *
     * @return swagger项目列表
     */
    List<SwaggerAccountBean> fetchTotalList();

    /**
     * 获取测试环境下的列表
     *
     * @return 测试环境下的项目列表
     */
    List<SwaggerTestEnvironmentBean> fetchTestList();

    /**
     * 查看项目是否存在
     *
     * @param projectName 项目名称
     * @return 是否存在
     */
    boolean checkProject(@Param("projectName") String projectName);

    /**
     * 获取某一项目的具体信息
     *
     * @param projectName 项目名
     * @return 项目信息
     */
    SwaggerAccountBean fetchProjectDetail(@Param("projectName") String projectName);

    /**
     * 更新项目信息
     *
     * @param swaggerCoreMessageBean 项目信息
     */
    void updateProject(@Param("swaggerCoreMessageBean") SwaggerCoreMessageBean swaggerCoreMessageBean);

    /**
     * 录入新的一条记录
     *
     * @param swaggerCoreMessageBean 项目信息
     */
    void inputNewProject(@Param("swaggerCoreMessageBean") SwaggerCoreMessageBean swaggerCoreMessageBean);

    /**
     * 删除一条记录
     *
     * @param projectName 项目名
     */
    void dropProject(@Param("projectName") String projectName);
}
