package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.phicomm.product.manger.dao.AdminUserInfoMapper;
import com.phicomm.product.manger.exception.*;
import com.phicomm.product.manger.model.user.AdminUserInfo;
import com.phicomm.product.manger.utils.FileUtil;
import com.phicomm.product.manger.utils.HttpUtil;
import com.phicomm.product.manger.utils.RegexUtil;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 用户管理服务
 *
 * @author yufei.liu
 */
@Service
public class UserMangerService {

    private static final Logger logger = Logger.getLogger(UserMangerService.class);

    private AdminUserInfoMapper adminUserInfoMapper;

    private static final String ADMINISTRATOR = "administrator";

    private static final String USER = "user";

    @Autowired
    public UserMangerService(AdminUserInfoMapper adminUserInfoMapper) {
        this.adminUserInfoMapper = adminUserInfoMapper;
        Assert.notNull(this.adminUserInfoMapper);
    }

    /**
     * 创建用户
     *
     * @param phoneNumber 手机号
     * @param email       邮箱
     * @param username    用户名
     * @param sex         性别
     * @param role        角色
     * @param headPicture 头像
     */
    public void createUser(String phoneNumber,
                           String email,
                           String username,
                           String sex,
                           String role,
                           MultipartFile headPicture) throws UploadFileException, UserHasExistException, DataFormatException, PermissionHasNotEnoughException {
        AdminUserInfo adminUserInfo = HttpUtil.getCurrentUserInfo();
        if (adminUserInfo == null || !ADMINISTRATOR.equalsIgnoreCase(adminUserInfo.getRole())) {
            throw new PermissionHasNotEnoughException();
        }
        check(phoneNumber, email, username, sex, role, headPicture);
        Map<String, String> result = FileUtil.uploadFileToHermes(headPicture);
        String hermesUrl = result.get("url");
        logger.info(String.format("phoneNumber = %s, email = %s, username = %s, sex = %s, role = %s, url = %s.",
                phoneNumber, email, username, sex, role, hermesUrl));
        int sexNumber = "boy".equalsIgnoreCase(sex) ? 1 : 0;
        adminUserInfoMapper.insert(phoneNumber, email, username, sexNumber, role, hermesUrl);
    }

    /**
     * 校验格式
     */
    private void check(String phoneNumber,
                       String email,
                       String username,
                       String sex,
                       String role,
                       MultipartFile headPicture) throws DataFormatException, UserHasExistException {
        if (!RegexUtil.checkPhoneNumber(phoneNumber)) {
            throw new DataFormatException();
        }
        if (!RegexUtil.checkEmail(email)) {
            throw new DataFormatException();
        }
        if (Strings.isNullOrEmpty(username)) {
            throw new DataFormatException();
        }
        if (!"boy".equalsIgnoreCase(sex) && !"girl".equalsIgnoreCase(sex)) {
            throw new DataFormatException();
        }
        if (!ADMINISTRATOR.equalsIgnoreCase(role) && !USER.equalsIgnoreCase(role)) {
            throw new DataFormatException();
        }
        AdminUserInfo adminUserInfo = adminUserInfoMapper.getUserInfo(phoneNumber);
        if (adminUserInfo != null) {
            throw new UserHasExistException();
        }
        if (headPicture.isEmpty()) {
            throw new DataFormatException();
        }
        String filename = headPicture.getOriginalFilename();
        if (!filename.endsWith(".jpg") && !filename.endsWith(".jpeg") && !filename.endsWith(".png")) {
            throw new DataFormatException();
        }
    }

    /**
     * 用户列表
     */
    public JSONObject userList() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        result.put("data", jsonArray);
        List<AdminUserInfo> adminUserInfoList = adminUserInfoMapper.getUserInfoList();
        if (adminUserInfoList != null) {
            adminUserInfoList.forEach((item) -> {
                Map<String, String> map = Maps.newHashMap();
                map.put("id", String.valueOf(item.getId()));
                map.put("username", item.getUsername());
                map.put("phoneNumber", item.getPhoneNumber());
                map.put("email", item.getEmail());
                map.put("role", item.getRole());
                map.put("createTime", new DateTime(item.getCreateTime()).toString("yyyy-MM-dd"));
                jsonArray.add(map);
            });
        }
        return result;
    }

    /**
     * 获得用户详情
     *
     * @param phoneNumber 手机号
     * @return 用户详情
     */
    public AdminUserInfo getUserDetail(String phoneNumber) {
        return adminUserInfoMapper.getUserInfo(phoneNumber);
    }

    /**
     * 删除用户
     * 1、首先自己需要是管理员
     * 2、被删除的用户不能是自己
     * 3、被删除的用户不能是管理员
     *
     * @param phoneNumber 手机号
     */
    public void deleteUser(String phoneNumber) throws PermissionHasNotEnoughException {
        AdminUserInfo adminUserInfo = HttpUtil.getCurrentUserInfo();
        if (adminUserInfo == null || !ADMINISTRATOR.equalsIgnoreCase(adminUserInfo.getRole())) {
            throw new PermissionHasNotEnoughException();
        }
        AdminUserInfo targetUserInfo = adminUserInfoMapper.getUserInfo(phoneNumber);
        if (!USER.equalsIgnoreCase(targetUserInfo.getRole())) {
            throw new PermissionHasNotEnoughException();
        }
        if (targetUserInfo.getPhoneNumber().equals(adminUserInfo.getPhoneNumber())) {
            throw new PermissionHasNotEnoughException();
        }
        adminUserInfoMapper.delete(phoneNumber);
    }

    /**
     * 修改用户信息
     *
     * @param phoneNumber 手机号
     * @param email       邮箱
     * @param username    用户名
     * @param sex         性别
     * @param role        系统角色
     * @param headPicture 头像
     */
    public void modifyUserInfo(String phoneNumber,
                               String email,
                               String username,
                               String sex,
                               String role,
                               MultipartFile headPicture)
            throws PermissionHasNotEnoughException, UserNotFoundException, UploadFileException {
        AdminUserInfo target = adminUserInfoMapper.getUserInfo(phoneNumber);
        if (target == null) {
            throw new UserNotFoundException();
        }
        AdminUserInfo adminUserInfo = HttpUtil.getCurrentUserInfo();
        if (adminUserInfo == null || (!ADMINISTRATOR.equalsIgnoreCase(adminUserInfo.getRole()) && !target.getPhoneNumber().equals(adminUserInfo.getPhoneNumber()))) {
            throw new PermissionHasNotEnoughException();
        }
        target.setPhoneNumber(phoneNumber);
        target.setEmail(email);
        target.setUsername(username);
        target.setSex("boy".equalsIgnoreCase(sex) ? 1 : 0);
        target.setRole(role);
        if (!headPicture.isEmpty()) {
            Map<String, String> hermesResult = FileUtil.uploadFileToHermes(headPicture);
            String url = hermesResult.get("url");
            target.setHeadPicture(url);
        }
        adminUserInfoMapper.update(target);
    }
}
