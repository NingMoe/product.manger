package com.phicomm.product.manger.controller.permission;

import com.phicomm.product.manger.module.permission.PermissionManager;
import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 权限管理接口
 * Created by yufei.liu on 2017/5/27.
 */
@Controller
public class PermissionController {

    private PermissionManager permissionManager;

    @Autowired
    public PermissionController(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
        Assert.notNull(this.permissionManager);
    }

    /**
     * 获取所有的权限列表
     *
     * @return 所有权限列表
     */
    @RequestMapping(value = "get/all/permissions", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("获取所有function规则")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 3, message = "用户不存在", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public Response<List<String>> getPermissions() {
        List<String> permissions = permissionManager.getAllPermissions();
        return new Response<List<String>>()
                .setStatus(0)
                .setData(permissions);
    }

}
