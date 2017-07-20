package com.phicomm.product.manger.controller.swagger;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.SwaggerProjectHasExistedException;
import com.phicomm.product.manger.exception.SwaggerProjectNotFoundException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.swagger.SwaggerAccountBean;
import com.phicomm.product.manger.model.swagger.SwaggerCoreMessageBean;
import com.phicomm.product.manger.service.SwaggerAccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Swagger账户管理
 * Created by wei.yang on 2017/7/19.
 */
@Controller
public class SwaggerAccountController {

    private SwaggerAccountService swaggerAccountService;

    @Autowired
    public SwaggerAccountController(SwaggerAccountService swaggerAccountService) {
        this.swaggerAccountService = swaggerAccountService;
        Assert.notNull(this.swaggerAccountService);
    }

    @RequestMapping(value = "swagger/project/list", method = {RequestMethod.POST, RequestMethod.GET},
            produces = "application/json")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "成功添加一条心记录", response = CommonResponse.class)
    })
    @ApiOperation("获取项目列表")
    @FunctionPoint("common")
    public Response<Object> fetchProjectList(HttpSession session) {
        return new Response<>().setData(swaggerAccountService.fetchProjectList(session));
    }

    /**
     * 删除一条记录
     *
     * @param projectName 项目名
     * @return 响应
     * @throws DataFormatException             数据格式异常
     * @throws SwaggerProjectNotFoundException 项目不存在
     */
    @RequestMapping(value = "swagger/project/delete", method = {RequestMethod.POST, RequestMethod.GET},
            produces = "application/json")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "成功添加一条心记录", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class),
            @ApiResponse(code = 13, message = "项目不存在", response = CommonResponse.class)
    })
    @ApiOperation("删除一行数据")
    @FunctionPoint("common")
    public CommonResponse fetchProjectList(@RequestParam String projectName) throws DataFormatException,
            SwaggerProjectNotFoundException {
        swaggerAccountService.deleteProject(projectName);
        return CommonResponse.ok();
    }

    /**
     * 编辑项目信息
     *
     * @param swaggerCoreMessageBean 相关信息
     * @return 是否成功
     * @throws DataFormatException             数据格式异常
     * @throws SwaggerProjectNotFoundException 项目不存在
     */
    @RequestMapping(value = "swagger/project/edit", method = {RequestMethod.POST, RequestMethod.GET},
            produces = "application/json")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "成功添加一条心记录", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class),
            @ApiResponse(code = 13, message = "项目不存在", response = CommonResponse.class)
    })
    @ApiOperation("编辑某个项目的信息")
    @FunctionPoint("common")
    public CommonResponse editProject(@RequestBody SwaggerCoreMessageBean swaggerCoreMessageBean) throws DataFormatException,
            SwaggerProjectNotFoundException {
        swaggerAccountService.updateProject(swaggerCoreMessageBean);
        return CommonResponse.ok();
    }

    /**
     * 获取某个项目的详情
     *
     * @param projectName 项目名
     * @return 项目详情
     * @throws SwaggerProjectNotFoundException 项目不存在
     */
    @RequestMapping(value = "swagger/project/detail", method = {RequestMethod.POST, RequestMethod.GET},
            produces = "application/json")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "成功添加一条心记录", response = CommonResponse.class),
            @ApiResponse(code = 13, message = "项目不存在", response = CommonResponse.class)
    })
    @ApiOperation("获取某个项目的详情")
    @FunctionPoint("common")
    public Response<SwaggerAccountBean> fetchProjectDetail(@RequestParam String projectName) throws SwaggerProjectNotFoundException {
        SwaggerAccountBean swaggerAccountBean = swaggerAccountService.fetchProjectDetail(projectName);
        return new Response<SwaggerAccountBean>().setData(swaggerAccountBean);
    }

    /**
     * 添加一个新的记录
     *
     * @param swaggerCoreMessageBean 项目信息
     * @return 响应
     * @throws DataFormatException               数据格式异常
     * @throws SwaggerProjectHasExistedException 项目已经存在
     */
    @RequestMapping(value = "swagger/project/insert", method = {RequestMethod.POST, RequestMethod.GET},
            produces = "application/json")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "成功添加一条心记录", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class),
            @ApiResponse(code = 14, message = "项目已经存在", response = CommonResponse.class)
    })
    @ApiOperation("添加一个新项目")
    @FunctionPoint("common")
    public CommonResponse insertProject(@RequestBody SwaggerCoreMessageBean swaggerCoreMessageBean)
            throws DataFormatException, SwaggerProjectHasExistedException {
        swaggerAccountService.insertNewProject(swaggerCoreMessageBean);
        return CommonResponse.ok();
    }
}
