package com.phicomm.product.manger.controller.group;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.GroupHasExistException;
import com.phicomm.product.manger.exception.UserNotFoundException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.group.GroupInfo;
import com.phicomm.product.manger.model.group.GroupUser;
import com.phicomm.product.manger.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组相关的接口
 *
 * @author qiang.ren
 * @date 2018/4/5
 */
@Controller
@Api(value = "组操作接口", description = "组操作接口")
public class GroupController {

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService){
        this.groupService = groupService;
        Assert.notNull(this.groupService);
    }

    /**
     * 添加组
     */
    @RequestMapping(value = "group/add", method = RequestMethod.POST,
            consumes = "multipart/form-data", produces = "application/json")
    @ApiOperation("添加组")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class),
            @ApiResponse(code = 28, message = "组存在异常", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public CommonResponse groupAdd(@RequestParam("groupName") String groupName,
                                   @RequestParam("groupType") String groupType,
                                   @RequestParam(value = "description", required = false) String description)
            throws DataFormatException, GroupHasExistException {
        groupService.groupAdd(groupName, groupType, description);
        return CommonResponse.ok();
    }

    /**
     * 获取组列表
     */
    @RequestMapping(value = "group/list", method = {RequestMethod.POST,RequestMethod.GET},
            produces = "application/json")
    @ApiOperation("获取组列表")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint(value = "common")
    public JSONObject groupList(){
        return groupService.groupList();
    }

    /**
     * 删除组
     */
    @RequestMapping(value = "group/delete", method = RequestMethod.POST,
            produces = "application/json")
    @ApiOperation("删除组")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint(value = "common")
    public CommonResponse groupDelete(@RequestParam("id") long id){
        groupService.groupDelete(id);
        return CommonResponse.ok();
    }

    /**
     * 更新组
     */
    @RequestMapping(value = "group/update", method = RequestMethod.POST,
            produces = "application/json")
    @ApiOperation("添加组")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public CommonResponse groupUpdate(@RequestParam("id") long id, @RequestParam("name") String name,
                                   @RequestParam("type") String type,
                                   @RequestParam("description") String description)
            throws DataFormatException {
        groupService.groupUpdate(id, name, type, description);
        return CommonResponse.ok();
    }

    /**
     * 组添加用户
     */
    @RequestMapping(value = "group/user/add", method = RequestMethod.POST,
            produces = "application/json")
    @ApiOperation("组添加用户")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class),
            @ApiResponse(code = 17, message = "没有该用户", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public CommonResponse groupUserAdd(@RequestParam("groupId") long groupId,
                                       @RequestParam("phoneNumber") String phoneNumber,
                                       @RequestParam("description") String description)
            throws DataFormatException, UserNotFoundException {
        groupService.groupUserAdd(groupId, phoneNumber, description);
        return CommonResponse.ok();
    }

    /**
     * 获取一个组内所有用户
     */
    @RequestMapping(value = "group/user/list", method = RequestMethod.POST,
            produces = "application/json")
    @ApiOperation("获取一个组内所有用户")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint(value = "common")
    public Response<List<GroupUser>> groupUserList(@RequestParam("groupId") long groupId){
        List<GroupUser> list = groupService.groupUserList(groupId);
        return new Response<List<GroupUser>>().setData(list);
    }

    /**
     * 删除组内一个用户
     */
    @RequestMapping(value = "group/user/delete", method = RequestMethod.POST,
            produces = "application/json")
    @ApiOperation("删除组内一个用户")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint(value = "common")
    public CommonResponse groupUserDelete(@RequestParam("groupId") long groupId,
                                          @RequestParam("userId") String userId){
        groupService.groupUserDelete(groupId, userId);
        return CommonResponse.ok();
    }


}
