package com.phicomm.product.manger.module.permission;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.phicomm.product.manger.dao.UserPermissionInfoMapper;
import com.phicomm.product.manger.utils.ExceptionUtil;
import com.phicomm.product.manger.config.SwaggerPropertiesConfig;
import com.phicomm.product.manger.enumeration.PermissionEnum;
import com.phicomm.product.manger.model.permission.PermissionModel;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * 权限管理
 * Created by yufei.liu on 2017/6/2.
 */
@Component
public class PermissionManager {

    private static final String PERMISSION_CONFIG_FILE = "permission.xml";

    private static final Logger logger = Logger.getLogger(PermissionManager.class);

    private List<PermissionModel> commonPermissions;

    private List<PermissionModel> specialPermissions;

    private UserPermissionInfoMapper userPermissionInfoMapper;

    private String adminUsername;

    @Autowired
    public PermissionManager(UserPermissionInfoMapper userPermissionInfoMapper,
                             SwaggerPropertiesConfig propertiesConfig) {
        commonPermissions = Lists.newArrayList();
        specialPermissions = Lists.newArrayList();
        this.adminUsername = propertiesConfig.getSwaggerLoginName();
        this.userPermissionInfoMapper = userPermissionInfoMapper;
        Assert.notNull(this.userPermissionInfoMapper);
        Assert.notNull(this.adminUsername);
    }

    /**
     * 读取配置文件
     */
    @PostConstruct
    public void init() {
        readConfigurationFile();
        print();
    }

    /**
     * 读配置文件，解析xml文件
     */
    private void readConfigurationFile() {
        URL permissionConfigFile = Resources.getResource(PERMISSION_CONFIG_FILE);
        SAXReader reader = new SAXReader();
        Document document;
        File configFile = new File(permissionConfigFile.getFile());
        try {
            document = reader.read(configFile);
        } catch (DocumentException e) {
            logger.error(ExceptionUtil.getErrorMessage(e));
            return;
        }
        parse(document, PermissionEnum.COMMON, "common-permission");
        parse(document, PermissionEnum.SPECIAL, "permissions");
    }

    /**
     * 解析数据
     */
    private void parse(Document document, PermissionEnum permissionEnum, String rootName) {
        Element permissionRoot = document.getRootElement().element(rootName);
        Iterator iterator = permissionRoot.elementIterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            String id = element.element("id").getTextTrim();
            String name = element.element("name").getTextTrim();
            String description = element.element("description").getTextTrim();
            if (permissionEnum == PermissionEnum.COMMON) {
                this.commonPermissions.add(new PermissionModel(
                        PermissionEnum.COMMON,
                        id, name, description
                ));
            } else {
                this.specialPermissions.add(new PermissionModel(
                        PermissionEnum.SPECIAL,
                        id, name, description
                ));
            }
        }
    }

    /**
     * 打印相关信息
     */
    private void print() {
        logger.info("common-permission:");
        commonPermissions.forEach(item -> logger.info(String.format("\t\t%s", item)));
        logger.info("special-permission:");
        specialPermissions.forEach(item -> logger.info(String.format("\t\t%s", item)));
    }

    /**
     * 获取所有权限
     */
    public List<String> getAllPermissions() {
        List<String> permissions = Lists.newArrayList();
        commonPermissions.forEach(item -> permissions.add(item.getId()));
        specialPermissions.forEach(item -> permissions.add(item.getId()));
        return permissions;
    }

    /**
     * 获取用户的权限列表
     */
    public List<String> getUserPermission(String phoneNumber) {
        if(adminUsername.equals(phoneNumber)) {
            return getAllPermissions();
        }
        List<String> permissions = userPermissionInfoMapper.getUserPermission(phoneNumber);
        return permissions == null ? Lists.newArrayList() : permissions;
    }

}
