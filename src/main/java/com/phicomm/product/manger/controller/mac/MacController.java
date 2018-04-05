package com.phicomm.product.manger.controller.mac;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.mac.BalanceDeviceModel;
import com.phicomm.product.manger.service.MacService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * MAC接口
 *
 * @author qiang.ren
 * @date 2018/3/28
 */
@Controller
public class MacController {

    private MacService macService;

    @Autowired
    public MacController(MacService macService){
        this.macService = macService;
        Assert.notNull(this.macService);
    }

    /**
     * 查询该mac地址对应的体脂称是否售出
     *
     * @return 已售出：true、未售出：false
     */
    @RequestMapping(value = "sold/mac/status", method = RequestMethod.POST,
            produces = "application/json")
    @ApiOperation("查询该mac地址对应的体脂称是否售出")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public Response<Boolean> getMacStatus(@RequestBody BalanceDeviceModel balanceDeviceModel,
            @RequestParam("environment") String environment)
            throws DataFormatException, NoSuchAlgorithmException, KeyManagementException, IOException {
        boolean macStatus = macService.getMacStatus(balanceDeviceModel, environment);
        return new Response<Boolean>().setStatus(0).setData(macStatus);
    }
}
