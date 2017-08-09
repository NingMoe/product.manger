package com.phicomm.product.manger.controller.essay;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.IdHasExistException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.service.EssayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 文章接口
 * Created by Qiang on 2017/8/7.
 */
@Controller
@Api(description = "文章接口")
public class EssayController {

    private EssayService essayService;

    @Autowired
    public EssayController(EssayService essayService) {
        this.essayService = essayService;
        Assert.notNull(this.essayService);
    }

    /**
     * 文章新增接口
     *
     * @param essayId    用户自定义文章ID
     * @param title      文章标题
     * @param subtitle   文章副标题
     * @param summary     文章简介
     * @param coverUrl    文章封面URL
     * @param contentUrl  文章正文URL
     * @param type         环境类型 test或prod
     * @return 成功返回0
     */
    @RequestMapping(value = "essay/upload", method = RequestMethod.POST,
            consumes = "multipart/form-data", produces = "application/json")
    @ApiOperation("文章上传接口")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class),
            @ApiResponse(code = 18, message = "ID已经存在", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public CommonResponse essayUpload(@RequestParam("essayId") String essayId,
                                      @RequestParam("title") String title,
                                      @RequestParam("subtitle") String subtitle,
                                      @RequestParam("summary") String summary,
                                      @RequestParam("coverUrl") String coverUrl,
                                      @RequestParam("contentUrl") String contentUrl,
                                      @RequestParam("type") String type)
            throws DataFormatException, IdHasExistException {
        essayService.essayUpload(essayId, title, subtitle, summary, coverUrl, contentUrl, type);
        return CommonResponse.ok();
    }

    /**
     * 文章更新接口
     *
     * @param essayId     用户自定义文章ID
     * @param title       文章标题
     * @param summary     文章简介
     * @param coverUrl    文章封面URL
     * @param contentUrl  文章正文URL
     * @param type         环境类型 test或prod
     * @return 成功返回0
     */
    @RequestMapping(value = "essay/update", method = RequestMethod.POST,
            consumes = "multipart/form-data", produces = "application/json")
    @ApiOperation("文章更新接口")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class),
            @ApiResponse(code = 18, message = "ID已经存在", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public CommonResponse essayUpdate(@RequestParam("essayId") String essayId,
                                      @RequestParam("title") String title,
                                      @RequestParam("subtitle") String subtitle,
                                      @RequestParam("summary") String summary,
                                      @RequestParam("coverUrl") String coverUrl,
                                      @RequestParam("contentUrl") String contentUrl,
                                      @RequestParam("type") String type)
            throws DataFormatException, IdHasExistException {
        essayService.essayUpdate(essayId, title, subtitle, summary, coverUrl, contentUrl, type);
        return CommonResponse.ok();
    }

    /**
     * 获取文章列表接口
     *
     * @param type      环境类型 test或prod
     * @return 文章列表
     */
    @RequestMapping(value = "essay/list/json", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    @ApiOperation("获取文章列表")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public JSONObject essayList(String type) throws DataFormatException {
        return essayService.essayList(type);
    }

    /**
     * 删除一篇文章接口
     *
     * @param essayId   用户自定义文章ID
     * @param type      环境类型 test或prod
     * @return 成功返回0
     */
    @RequestMapping(value = "essay/delete", method = {RequestMethod.POST, RequestMethod.GET},
            produces = "application/json")
    @ApiOperation("删除一篇文章")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public CommonResponse essayDelete(@RequestParam("essayId") String essayId, @RequestParam("type") String type)
            throws DataFormatException {
        essayService.essayDelete(essayId, type);
        return CommonResponse.ok();
    }
}
