package com.phicomm.product.manger.service;

import com.google.common.base.Strings;
import com.phicomm.product.manger.dao.SwaggerManagerMapper;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.SwaggerProjectHasExistedException;
import com.phicomm.product.manger.exception.SwaggerProjectNotFoundException;
import com.phicomm.product.manger.model.swagger.SwaggerAccountBean;
import com.phicomm.product.manger.model.swagger.SwaggerCoreMessageBean;
import com.phicomm.product.manger.model.swagger.SwaggerTestEnvironmentBean;
import com.phicomm.product.manger.model.user.AdminUserInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * swagger账户管理
 * Created by wei.yang on 2017/7/19.
 */
@Service
public class SwaggerAccountService {

    private static final Logger logger = Logger.getLogger(SwaggerAccountService.class);

    private SwaggerManagerMapper swaggerManagerMapper;

    @Autowired
    public SwaggerAccountService(SwaggerManagerMapper swaggerManagerMapper) {
        this.swaggerManagerMapper = swaggerManagerMapper;
        Assert.notNull(this.swaggerManagerMapper);
    }

    /**
     * 获取列表
     *
     * @param session session，获取用户类型
     * @return 列表
     */
    public Object fetchProjectList(HttpSession session) {
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        logger.info(adminUserInfo);
        String role = Strings.isNullOrEmpty(adminUserInfo.getRole()) ? "" : adminUserInfo.getRole();
        if ("administrator".equalsIgnoreCase(role)) {
            return fetchTotalList();
        } else {
            return fetchTestList();
        }
    }

    /**
     * 获取某一个项目的具体信息
     *
     * @param projectName 项目名称
     * @return 具体信息
     */
    public SwaggerAccountBean fetchProjectDetail(String projectName) throws SwaggerProjectNotFoundException {
        boolean exist = swaggerManagerMapper.checkProject(projectName);
        if (!exist) {
            throw new SwaggerProjectNotFoundException();
        }
        return swaggerManagerMapper.fetchProjectDetail(projectName);
    }

    /**
     * 更新一条数据
     *
     * @param swaggerCoreMessageBean 数据信息
     * @throws DataFormatException             数据格式异常
     * @throws SwaggerProjectNotFoundException 项目不存在
     */
    public void updateProject(SwaggerCoreMessageBean swaggerCoreMessageBean) throws DataFormatException,
            SwaggerProjectNotFoundException {
        System.out.println(swaggerCoreMessageBean);
        checkSwaggerBean(swaggerCoreMessageBean);
        boolean exist = swaggerManagerMapper.checkProject(swaggerCoreMessageBean.getProjectName());
        if (!exist) {
            throw new SwaggerProjectNotFoundException();
        }
        swaggerManagerMapper.updateProject(swaggerCoreMessageBean);
    }

    /**
     * 写入一条新的记录
     *
     * @param swaggerCoreMessageBean 项目信息
     * @throws DataFormatException               数据格式异常
     * @throws SwaggerProjectHasExistedException 项目已经存在
     */
    public void insertNewProject(SwaggerCoreMessageBean swaggerCoreMessageBean) throws DataFormatException,
            SwaggerProjectHasExistedException {
        checkSwaggerBean(swaggerCoreMessageBean);
        boolean exist = swaggerManagerMapper.checkProject(swaggerCoreMessageBean.getProjectName());
        if (exist) {
            throw new SwaggerProjectHasExistedException();
        }
        swaggerManagerMapper.inputNewProject(swaggerCoreMessageBean);
    }

    /**
     * 删除某一个项目
     *
     * @param projectName 项目名
     * @throws DataFormatException             数据格式异常
     * @throws SwaggerProjectNotFoundException 项目没有找到
     */
    public void deleteProject(String projectName) throws DataFormatException, SwaggerProjectNotFoundException {
        if (Strings.isNullOrEmpty(projectName)) {
            throw new DataFormatException();
        }
        boolean exist = swaggerManagerMapper.checkProject(projectName);
        if (!exist) {
            throw new SwaggerProjectNotFoundException();
        }
        swaggerManagerMapper.dropProject(projectName);
    }

    /**
     * 获取全部的信息
     *
     * @return 全部的项目信息
     */
    private List<SwaggerAccountBean> fetchTotalList() {
        return swaggerManagerMapper.fetchTotalList();
    }

    /**
     * 获取测试列表
     *
     * @return 测试环境的项目列表信息
     */
    private List<SwaggerTestEnvironmentBean> fetchTestList() {
        return swaggerManagerMapper.fetchTestList();
    }

    /**
     * 核对bean的数据是否符合要求
     *
     * @param swaggerCoreMessageBean 数据
     * @throws DataFormatException 数据格式异常
     */
    private void checkSwaggerBean(SwaggerCoreMessageBean swaggerCoreMessageBean) throws DataFormatException {
        if (swaggerCoreMessageBean == null) {
            throw new DataFormatException();
        }
        if (Strings.isNullOrEmpty(swaggerCoreMessageBean.getProjectName()) ||
                Strings.isNullOrEmpty(swaggerCoreMessageBean.getProjectAddress()) ||
                Strings.isNullOrEmpty(swaggerCoreMessageBean.getSwaggerAddressProd()) ||
                Strings.isNullOrEmpty(swaggerCoreMessageBean.getSwaggerPasswordProd()) ||
                Strings.isNullOrEmpty(swaggerCoreMessageBean.getSwaggerUsernameProd()) ||
                Strings.isNullOrEmpty(swaggerCoreMessageBean.getSwaggerAddressTest()) ||
                Strings.isNullOrEmpty(swaggerCoreMessageBean.getSwaggerPasswordTest()) ||
                Strings.isNullOrEmpty(swaggerCoreMessageBean.getSwaggerUsernameTest())) {
            throw new DataFormatException();
        }
    }
}
