package com.phicomm.product.manger.controller.balance.ota;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.VersionNotExistException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.ota.BalanceOtaInfo;
import com.phicomm.product.manger.model.ota.BalanceOtaStatus;
import com.phicomm.product.manger.service.BalanceOtaService;
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
 * Ota
 * Created by wei.yang on 2017/7/10.
 */
@Controller
public class BalanceOtaController {

    private BalanceOtaService balanceOtaService;

    @Autowired
    public BalanceOtaController(BalanceOtaService balanceOtaService) {
        this.balanceOtaService = balanceOtaService;
        Assert.notNull(this.balanceOtaService);
    }

    /**
     * 上传Ota版本信息
     *
     * @param version     版本号
     * @param aFile       a类型文件
     * @param bFile       b类型文件
     * @param environment 环境（测试或生产)
     * @return Ota版本信息
     * @throws IOException         io异常（MultipartFile类型参数不存在或上传文件出错）
     * @throws DataFormatException 数据格式异常
     */
    @RequestMapping(value = "balance/ota/upload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传Ota包信息")
    @FunctionPoint(value = "common")
    public Response<BalanceOtaInfo> uploadOtaMessage(@RequestParam int version,
                                                     @RequestParam MultipartFile aFile,
                                                     @RequestParam MultipartFile bFile,
                                                     @RequestParam String environment)
            throws IOException, DataFormatException {
        return new Response<BalanceOtaInfo>().setData(balanceOtaService.uploadOtaMessage(aFile, bFile, version, environment));
    }

    /**
     * 更新版本状态并触发升级
     *
     * @param balanceOtaStatus 版本状态
     * @return 触发升级失败的主机
     * @throws VersionNotExistException 版本已经存在
     * @throws IOException              socket读写异常
     * @throws DataFormatException      数据格式异常
     */
    @RequestMapping(value = "balance/ota/status/update/trigger", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ApiOperation("修改版本状态")
    @FunctionPoint(value = "common")
    public Response<List<HostAndPort>> updateOtaStatusAndTrigger(@RequestBody BalanceOtaStatus balanceOtaStatus)
            throws VersionNotExistException, IOException, DataFormatException {
        return new Response<List<HostAndPort>>().setData(balanceOtaService.updateStatusAndTrigger(balanceOtaStatus));
    }

    /**
     * 更新版本状态并触发升级
     *
     * @param balanceOtaStatus 版本状态
     * @return 操作状态
     * @throws DataFormatException      数据格式异常
     */
    @RequestMapping(value = "balance/ota/update/change", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ApiOperation("修改版本状态")
    @FunctionPoint(value = "common")
    public CommonResponse updateOtaStatus(@RequestBody BalanceOtaStatus balanceOtaStatus)
            throws IOException, DataFormatException {
        balanceOtaService.updateBalanceOtaStatus(balanceOtaStatus);
        return CommonResponse.ok();
    }

    /**
     * 获取某个环境下的Ota列表
     *
     * @param environment 环境
     * @return Ota信息列表
     */
    @RequestMapping(value = "balance/ota/list", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ApiOperation("获取Ota版本列表")
    @FunctionPoint(value = "common")
    public List<BalanceOtaInfo> fetchOtaList(@RequestParam String environment) {
        return balanceOtaService.fetchOtaList(environment);
    }

    /**
     * 获取某个环境下的Ota列表
     *
     * @param environment 环境
     * @return Ota信息列表
     */
    @RequestMapping(value = "balance/ota/list/json", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    @ResponseBody
    @ApiOperation("获取Ota版本列表")
    @FunctionPoint(value = "common")
    public JSONObject fetchOtaVersionList(@RequestParam String environment) {
        return balanceOtaService.fetchOtaVersionList(environment);
    }
}
