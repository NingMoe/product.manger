package com.phicomm.product.manger.controller.ota;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.ota.BalanceOtaInfo;
import com.phicomm.product.manger.service.BalanceOtaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

<<<<<<< Updated upstream
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
=======
>>>>>>> Stashed changes
    @RequestMapping(value = "balance/ota/upload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传Ota包信息")
    @FunctionPoint(value = "common")
    public Response<BalanceOtaInfo> uploadOtaMessage(@RequestParam int version,
                                                     @RequestParam MultipartFile aFile,
<<<<<<< Updated upstream
                                                     @RequestParam MultipartFile bFile,
                                                     @RequestParam String environment)
            throws IOException, DataFormatException {
        return new Response<BalanceOtaInfo>().setData(balanceOtaService.uploadOtaMessage(aFile, bFile, version, environment));
=======
                                                     @RequestParam MultipartFile bFile)
            throws IOException, DataFormatException {
        return new Response<BalanceOtaInfo>().setData(balanceOtaService.uploadOtaMessage(aFile, bFile, version));
>>>>>>> Stashed changes
    }
}
