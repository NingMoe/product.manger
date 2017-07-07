package com.phicomm.product.manger.controller.hermes;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.service.HermesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * 文件上传
 * <p>
 * Created by yufei.liu on 2017/7/4.
 */
@Controller
public class HermesController {

    private HermesService hermesService;

    @Autowired
    public HermesController(HermesService hermesService) {
        this.hermesService = hermesService;
        Assert.notNull(this.hermesService);
    }

    /**
     * 上传文件接口
     */
    @RequestMapping(value = "hermes/upload/file", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传文件到hermes")
    @FunctionPoint(value = "common")
    public Response<Map<String, Object>> uploadFile(@RequestPart("file") MultipartFile file) {
        Map<String, Object> map;
        try {
            map = hermesService.uploadFile(file);
        } catch (IOException e) {
            return new Response<Map<String, Object>>().setStatus(1).setData(null);
        }
        return new Response<Map<String, Object>>().setStatus(0).setData(map);
    }

}
