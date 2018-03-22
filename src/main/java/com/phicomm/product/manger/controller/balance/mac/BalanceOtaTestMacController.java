package com.phicomm.product.manger.controller.balance.mac;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.service.BalanceTestMacService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 添加测试mac
 *
 * @author wei.yang on 2017/7/13.
 */
@Controller
public class BalanceOtaTestMacController {

    private BalanceTestMacService balanceTestMacService;

    @Autowired
    public BalanceOtaTestMacController(BalanceTestMacService balanceTestMacService) {
        this.balanceTestMacService = balanceTestMacService;
        Assert.notNull(this.balanceTestMacService);
    }

    /**
     * 写入测试mac
     *
     * @param environment  环境
     * @param macList      mac列表
     * @param production   产品型号
     * @param firmwareType 固件类型
     * @return 响应
     * @throws DataFormatException 数据格式异常
     */
    @RequestMapping(value = "balance/test/mac", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ApiOperation("测试mac地址")
    @FunctionPoint("common")
    public CommonResponse insertMac(@RequestParam String environment,
                                    @RequestParam String macList,
                                    @RequestParam String production,
                                    @RequestParam String firmwareType) throws DataFormatException {
        balanceTestMacService.insertMac(macList, environment, production, firmwareType);
        return CommonResponse.ok();
    }
}
