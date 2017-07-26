package com.phicomm.product.manger.service;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import com.phicomm.product.manger.exception.LoginException;
import com.phicomm.product.manger.config.SwaggerPropertiesConfig;
import com.phicomm.product.manger.dao.AdminUserInfoMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.login.AccountModel;
import com.phicomm.product.manger.model.user.AdminUserInfo;
import com.phicomm.product.manger.module.account.PhicommAccount;
import com.phicomm.product.manger.module.permission.PermissionManager;
import com.phicomm.product.manger.utils.ExceptionUtil;
import com.phicomm.product.manger.utils.RegexUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 登陆的业务逻辑
 * Created by yufei.liu on 2017/5/31.
 */
@Service
public class LoginService {

    private final static Logger logger = Logger.getLogger(LoginService.class);

    private PhicommAccount phicommAccount;

    private AdminUserInfoMapper adminUserInfoMapper;

    private PermissionManager permissionManager;

    private String adminUsername;

    @Autowired
    public LoginService(PhicommAccount phicommAccount,
                        SwaggerPropertiesConfig propertiesConfig,
                        AdminUserInfoMapper adminUserInfoMapper,
                        PermissionManager permissionManager) {
        this.phicommAccount = phicommAccount;
        this.adminUsername = propertiesConfig.getSwaggerLoginName();
        this.adminUserInfoMapper = adminUserInfoMapper;
        this.permissionManager = permissionManager;
        Assert.notNull(this.phicommAccount);
        Assert.notNull(this.adminUsername);
        Assert.notNull(this.adminUserInfoMapper);
        Assert.notNull(this.permissionManager);
        logger.info(String.format("adminUsername = %s", adminUsername));
    }

    /**
     * 登陆
     */
    public boolean login(AccountModel accountModel) throws DataFormatException, LoginException {
        checkLoginParam(accountModel);
        String phoneNumber = accountModel.getPhoneNumber();
        String password = accountModel.getPassword();
        Map<String, String> userInfo;
        try {
            userInfo = phicommAccount.login(phoneNumber, password);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getErrorMessage(e));
            throw new LoginException();
        }
        AdminUserInfo adminUserInfo = adminUserInfoMapper.getUserInfo(phoneNumber);
        if (userInfo == null || adminUserInfo == null) {
            return false;
        }
        List<String> permissionList = permissionManager.getUserPermission(phoneNumber);
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attrs.getRequest().getSession();
        session.setAttribute(SessionKeyEnum.LOGIN_FLAG.getKeyName(), accountModel);
        session.setAttribute(SessionKeyEnum.USER_INFO_ACCOUNT.getKeyName(), userInfo);
        session.setAttribute(SessionKeyEnum.USER_INFO.getKeyName(), adminUserInfo);
        session.setAttribute(SessionKeyEnum.USER_PERMISSIONS.getKeyName(), Sets.newHashSet(permissionList));
        logger.info("session = " + session);
        logger.info("LOGIN_FLAG = " + accountModel);
        logger.info("USER_INFO_ACCOUNT = " + userInfo);
        logger.info("USER_INFO = " + adminUserInfo);
        logger.info("USER_PERMISSIONS = " + permissionList);
        return true;
    }

    /**
     * 校验登陆用户名和密码
     * 一般用户的用户名是手机号，超级管理员的用户名是admin
     *
     * @param accountModel 用户名密码
     * @throws DataFormatException 数据异常
     */
    private void checkLoginParam(AccountModel accountModel) throws DataFormatException {
        logger.info(String.format("login information : %s", accountModel));
        if (accountModel == null) {
            throw new DataFormatException();
        }
        String phoneNumber = accountModel.getPhoneNumber();
        String password = accountModel.getPassword();
        if (!RegexUtil.checkPhoneNumber(phoneNumber) && !adminUsername.equals(phoneNumber)) {
            throw new DataFormatException();
        }
        if (Strings.isNullOrEmpty(password)) {
            throw new DataFormatException();
        }
    }

    /**
     * 登出接口
     */
    public void logout() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attrs.getRequest().getSession();
        if (session != null) {
            Enumeration<String> enumeration = session.getAttributeNames();
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                session.removeAttribute(key);
            }
        }
    }

}
