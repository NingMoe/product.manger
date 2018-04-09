package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.phicomm.product.manger.dao.GroupMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.GroupHasExistException;
import com.phicomm.product.manger.exception.UserNotFoundException;
import com.phicomm.product.manger.model.group.GroupInfo;
import com.phicomm.product.manger.model.group.GroupUser;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 组业务逻辑
 *
 * @author qiang.ren
 * @date 2018/4/5
 */
@Service
public class GroupService {

    private static final Logger logger = Logger.getLogger(GroupService.class);

    private GroupMapper groupMapper;

    @Autowired
    public GroupService(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
        Assert.notNull(this.groupMapper);
    }

    /**
     * 添加组
     */
    public void groupAdd(String name, String type, String description) throws DataFormatException, GroupHasExistException {
        if (Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(type) || Strings.isNullOrEmpty(description)) {
            throw new DataFormatException();
        }
        if (groupMapper.isExistGroup(name, type)) {
            throw new GroupHasExistException();
        }
        logger.info(String.format("name=%s,type=%s,description=%s", name, type, description));
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setName(name);
        groupInfo.setType(type);
        groupInfo.setDescription(description);
        groupMapper.groupAdd(groupInfo);
    }

    /**
     * 获取组列表
     *
     * @return 组列表
     */
    public JSONObject groupList() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        result.put("data", jsonArray);
        List<GroupInfo> list = groupMapper.groupList();
        if (list != null) {
            list.forEach((item) -> {
                Map<String, String> map = Maps.newHashMap();
                map.put("id", item.getId() + "");
                map.put("name", item.getName());
                map.put("type", item.getType());
                map.put("memberNumber", item.getMemberNumber() + "");
                map.put("description", item.getDescription());
                map.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getCreateTime()));
                map.put("updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()));
                jsonArray.add(map);
            });
        }
        return result;
    }

    /**
     * 删除组
     *
     * @param id 组id
     */
    public void groupDelete(long id) {
        logger.info(String.format("id=%s", id));
        groupMapper.groupUserDelete2(id);
        groupMapper.groupDelete(id);
    }

    /**
     * 更新组
     */
    public void groupUpdate(long id, String name, String type, String description) throws DataFormatException {
        if (Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(type) || Strings.isNullOrEmpty(description)) {
            throw new DataFormatException();
        }
        logger.info(String.format("id=%s,name=%s,type=%s,description=%s", id, name, type, description));
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setId(id);
        groupInfo.setName(name);
        groupInfo.setType(type);
        groupInfo.setDescription(description);
        groupMapper.groupUpdate(groupInfo);
    }

    /**
     * 组添加用户
     */
    public void groupUserAdd(long groupId, String phoneNumber, String description) throws DataFormatException, UserNotFoundException {
        if (Strings.isNullOrEmpty(phoneNumber) || Strings.isNullOrEmpty(description)) {
            throw new DataFormatException();
        }
        String userId = getUserId(phoneNumber);
        if (Strings.isNullOrEmpty(userId)) {
            throw new UserNotFoundException();
        }
        logger.info(String.format("groupId=%s,phoneNumber=%s,description=%s", groupId, phoneNumber, description));
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId);
        groupUser.setUserId(userId);
        groupUser.setDescription(description);
        int n = groupMapper.groupUserAdd(groupUser);
        if (1 == n) {
            groupMapper.addMemberCount(groupId);
        }
    }

    /**
     * 获取一个组内所有用户
     *
     * @return 组内所有用户
     */
    public List<GroupUser> groupUserList(long groupId) {
        return groupMapper.groupUserList(groupId);
    }

    /**
     * 删除组内一个用户
     */
    public void groupUserDelete(long groupId, String userId) {
        logger.info(String.format("groupId=%s, userId=%s", groupId, userId));
        int n = groupMapper.groupUserDelete(groupId, userId);
        if (1 == n) {
            groupMapper.subtractMemberCount(groupId);
        }
    }

    /**
     * 通过电话号码获取用户id
     *
     * @param phoneNumber 电话号码
     * @return 用户id
     */
    private String getUserId(String phoneNumber) {
        String userId;
        CustomerContextHolder.selectProdDataSource();
        userId = groupMapper.getUserId(phoneNumber);
        CustomerContextHolder.clearDataSource();
        return userId;
    }
}
