package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.group.GroupInfo;
import com.phicomm.product.manger.model.group.GroupUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组数据操作
 *
 * @author qiang.ren
 * @date 2018/4/5
 */
@Repository
public interface GroupMapper {

    /**
     * 添加组
     * @param groupInfo 组信息
     */
    void groupAdd(@Param("groupInfo") GroupInfo groupInfo);

    /**
     * 是否已经存在改组
     */
    boolean isExistGroup(@Param("name") String name, @Param("type") String type);

    /**
     * 获取组列表
     * @return 组列表
     */
    List<GroupInfo> groupList();

    /**
     * 删除组
     * @param id 组id
     */
    void groupDelete(@Param("id") long id);

    /**
     * 更新组
     * @param groupInfo 组信息
     */
    void groupUpdate(@Param("groupInfo") GroupInfo groupInfo);

    /**
     * member_number + 1
     */
    void addMemberCount(@Param("groupId") long groupId);

    /**
     * member_number - 1
     */
    void subtractMemberCount(@Param("groupId") long groupId);

    /**
     * 组添加用户
     * @param groupUser 组&用户信息
     */
    int groupUserAdd(@Param("groupUser") GroupUser groupUser);

    /**
     * 获取一个组内所有用户
     * @return 组内所有用户
     */
    List<GroupUser> groupUserList(@Param("groupId") long groupId);

    /**
     * 删除组内一个用户
     */
    int groupUserDelete(@Param("groupId") long groupId, @Param("userId") String userId);

    /**
     * 删除组内所有用户
     */
    int groupUserDelete2(@Param("groupId") long groupId);

    /**
     * 通过电话号码获取用户id
     * @param phoneNumber 电话号码
     * @return 用户id
     */
    String getUserId(@Param("phoneNumber") String phoneNumber);
}
