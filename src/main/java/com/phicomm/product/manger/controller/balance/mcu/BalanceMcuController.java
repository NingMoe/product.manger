package com.phicomm.product.manger.controller.balance.mcu;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.mcu.BalanceMcuBean;
import com.phicomm.product.manger.model.mcu.BalanceMcuStatus;
import com.phicomm.product.manger.service.BalanceMcuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.HostAndPort;

import java.io.IOException;
import java.util.List;

/**
 * mcu升级
 * Created by wei.yang on 2017/7/17.
 */
@Controller
public class BalanceMcuController {

    private BalanceMcuService balanceMcuService;

    @Autowired
    public BalanceMcuController(BalanceMcuService balanceMcuService) {
        this.balanceMcuService = balanceMcuService;
        Assert.notNull(this.balanceMcuService);
    }

    @RequestMapping(value = "balance/mcu/upload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传Mcu包信息")
    @FunctionPoint(value = "common")
    public Response<BalanceMcuBean> uploadMcuMessage(@RequestParam int version,
                                                     @RequestParam MultipartFile file,
                                                     @RequestParam String environment)
            throws IOException, DataFormatException {
        return new Response<BalanceMcuBean>().setData(balanceMcuService.uploadMcuMessage(file, version, environment));
    }

    /**
     * 更新版本状态并触发升级
     *
     * @param balanceMcuStatus 版本状态
     * @return 触发升级失败的主机
     * @throws IOException         socket读写异常
     * @throws DataFormatException 数据格式异常
     */
    @RequestMapping(value = "balance/mcu/status/update/trigger", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    @ApiOperation("修改版本状态")
    @FunctionPoint(value = "common")
    public Response<List<HostAndPort>> updateMcuStatusAndTrigger(@RequestBody BalanceMcuStatus balanceMcuStatus)
            throws IOException, DataFormatException {
        return new Response<List<HostAndPort>>().setData(balanceMcuService.updateStatusAndTrigger(balanceMcuStatus));
    }

    /**
     * 更新版本状态并触发升级
     *
     * @param balanceMcuStatus 版本状态
     * @return 操作状态
     * @throws DataFormatException 数据格式异常
     */
    @RequestMapping(value = "balance/mcu/update/change", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    @ApiOperation("修改版本状态")
    @FunctionPoint(value = "common")
    public CommonResponse updateMcuStatus(@RequestBody BalanceMcuStatus balanceMcuStatus)
            throws IOException, DataFormatException {
        balanceMcuService.updateBalanceMcuStatus(balanceMcuStatus);
        return CommonResponse.ok();
    }

    /**
     * 获取某个环境下的Mcu列表
     *
     * @param environment 环境
     * @return Ota信息列表
     */
    @RequestMapping(value = "balance/mcu/list", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    @ApiOperation("获取Ota版本列表")
    @FunctionPoint(value = "common")
    public List<BalanceMcuBean> fetchMcuList(@RequestParam String environment) {
        return balanceMcuService.fetchMcuList(environment);
    }

    /**
     * 获取某个环境下的Ota列表
     *
     * @param environment 环境
     * @return Ota信息列表
     */
    @RequestMapping(value = "balance/mcu/list/json", method = {RequestMethod.POST, RequestMethod.GET},
            produces = "application/json")
    @ResponseBody
    @ApiOperation("获取Ota版本列表")
    @FunctionPoint(value = "common")
    public Response<List<BalanceMcuBean>> fetchMcuVersionList(@RequestParam String environment) {
        List<BalanceMcuBean> mcuList = balanceMcuService.fetchMcuVersionList(environment);
        return new Response<List<BalanceMcuBean>>().setData(mcuList);
    }
}
