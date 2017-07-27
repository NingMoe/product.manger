package com.phicomm.product.manger.controller.permission;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.*;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.user.AdminUserInfo;
import com.phicomm.product.manger.service.UserMangerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户管理接口
 *
 * @author yufei.liu
 */
@Controller
@Api(description = "用户管理接口")
public class UserMangerController {

    private UserMangerService userMangerService;

    @Autowired
    private UserMangerController(UserMangerService userMangerService) {
        this.userMangerService = userMangerService;
        Assert.notNull(this.userMangerService);
    }

    /**
     * 创建一个用户
     *
     * @return 数据
     */
    @RequestMapping(value = "user/manger/create", method = RequestMethod.POST,
            consumes = "multipart/form-data", produces = "application/json")
    @ResponseBody
    @ApiOperation("创建一个用户")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = Response.class),
            @ApiResponse(code = 7, message = "文件上传失败", response = Response.class),
            @ApiResponse(code = 15, message = "手机号已经存在", response = Response.class)
    })
    @FunctionPoint("common")
    public CommonResponse createUser(@RequestParam("phoneNumber") String phoneNumber,
                                     @RequestParam("email") String email,
                                     @RequestParam("username") String username,
                                     @RequestParam("sex") String sex,
                                     @RequestParam("role") String role,
                                     @RequestParam("headPicture") MultipartFile headPicture)
            throws UploadFileException, UserHasExistException, DataFormatException {
        userMangerService.createUser(phoneNumber, email, username, sex, role, headPicture);
        return CommonResponse.ok();
    }

    /**
     * 用户列表
     *
     * @return 数据
     */
    @RequestMapping(value = "user/manger/list", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    @ApiOperation("用户列表")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = Response.class)
    })
    @FunctionPoint("common")
    public JSONObject userList() throws DataFormatException {
        return userMangerService.userList();
    }

    /**
     * 用户详情
     *
     * @return 数据
     */
    @RequestMapping(value = "user/manger/detail", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    @ApiOperation("用户详情")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = Response.class)
    })
    @FunctionPoint("common")
    public Response<AdminUserInfo> getUserDetail(@Param("phoneNumber") String phoneNumber) throws DataFormatException {
        AdminUserInfo adminUserInfo = userMangerService.getUserDetail(phoneNumber);
        return new Response<AdminUserInfo>().setData(adminUserInfo);
    }

    /**
     * 删除用户
     *
     * @return 状态
     */
    @RequestMapping(value = "user/manger/delete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ApiOperation("删除用户")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = Response.class),
            @ApiResponse(code = 16, message = "权限不够", response = Response.class)
    })
    @FunctionPoint("common")
    public CommonResponse deleteUser(@Param("phoneNumber") String phoneNumber)
            throws DataFormatException, PermissionHasNotEnoughException {
        userMangerService.deleteUser(phoneNumber);
        return CommonResponse.ok();
    }

    /**
     * 修改用户信息
     *
     * @return 状态
     */
    @RequestMapping(value = "user/manger/modify", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ApiOperation("修改用户信息")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = Response.class),
            @ApiResponse(code = 7, message = "文件上传失败", response = Response.class),
            @ApiResponse(code = 16, message = "权限不够", response = Response.class),
            @ApiResponse(code = 17, message = "用户找不到", response = Response.class),
    })
    @FunctionPoint("common")
    public CommonResponse modifyUserInfo(@RequestParam("phoneNumber") String phoneNumber,
                                         @RequestParam("email") String email,
                                         @RequestParam("username") String username,
                                         @RequestParam("sex") String sex,
                                         @RequestParam("role") String role,
                                         @RequestParam("headPicture") MultipartFile headPicture)
            throws DataFormatException, PermissionHasNotEnoughException, UserNotFoundException, UploadFileException {
        userMangerService.modifyUserInfo(phoneNumber, email, username, sex, role, headPicture);
        return CommonResponse.ok();
    }

}
