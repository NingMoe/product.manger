package com.phicomm.product.manger.controller.picture;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.IdHasExistException;
import com.phicomm.product.manger.exception.UploadFileException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.picture.PictureUpload;
import com.phicomm.product.manger.service.PictureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;



/**
 * Created by xiang.zhang on 2017/9/6.
 */
@Controller
public class PictureController {

    private PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
        Assert.notNull(this.pictureService);
    }

    /**
     * 上传图片
     * @param file
     * @param
     * @return
     * @throws DataFormatException
     * @throws IdHasExistException
     * @throws IOException
     */

    @RequestMapping(value = "picture/upload/file", method = RequestMethod.POST)
    @ApiOperation("图片上传接口")
    @ResponseBody
    @FunctionPoint(value = "common")
    public CommonResponse pictureUpload(@RequestPart("file") MultipartFile[] file,
                                        @RequestParam("picId")       int[] picId,
                                        @RequestParam("picChiName") String[] picChiName,
                                        @RequestParam("picEngName") String[] picEngName,
                                        @RequestParam("picVersion") String picVersion)
            throws DataFormatException, IdHasExistException, IOException, UploadFileException, NoSuchAlgorithmException, KeyManagementException, SQLException {
        pictureService.pictureUploadNumber(file,picId,picChiName,picEngName,picVersion);
        return CommonResponse.ok();
    }

    /**
     *"返回图片详情
     * @param
     * @param picVersion
     * @return
     * @throws DataFormatException
     */
    @RequestMapping(value = "/picture/profile", method = RequestMethod.POST,
                    consumes = "multipart/form-data", produces = "application/json")
    @ResponseBody
    @ApiOperation(value = "返回图片详情")
    @PublicInterface
    public List<PictureUpload> pictureUpload(@RequestParam("picVersion") String picVersion) throws DataFormatException {
        List<PictureUpload> pictureList = pictureService.pictureList(picVersion);
        return pictureList;
    }

    /**
     *返回图片文件压缩包
     * @param picVersion
     * @return
     * @throws IOException
     * @throws DataFormatException
     */
    @RequestMapping(value = "/picture/download/file",method = RequestMethod.POST)
    @ApiOperation("图片文件压缩包返回")
    @PublicInterface
    public String downLoadFile(@RequestParam("picVersion") String picVersion) throws IOException, DataFormatException {

        List<PictureUpload> pictureList = pictureService.pictureList(picVersion);
        if (pictureList.size() == 0)
            throw new FileNotFoundException();
        return pictureList.get(0).getPicUrl();
    }

    /**
     *获取接口配置信息
     * @return
     */
    @RequestMapping(value = "picture/upload/config/get", method = {RequestMethod.POST, RequestMethod.GET},
            produces = "application/json")
    @ResponseBody
    @ApiOperation("获取配置信息")
    @FunctionPoint(value = "common")
    public CommonResponse getFirmwareConfig() {
        String configuation = pictureService.getPictureConfig();
        return CommonResponse.ok().setDescription(configuation);
    }

    /**
     *设置接口配置信息
     * @param configuation
     * @return
     */

    @RequestMapping(value = "picture/upload/config/set", method = {RequestMethod.POST, RequestMethod.GET},
            produces = "application/json")
    @ResponseBody
    @ApiOperation("设置配置信息")
    @FunctionPoint(value = "common")
    public CommonResponse setPictureConfig(@RequestParam("configuation") String configuation) {
        pictureService.setPictureConfig(configuation);
        return CommonResponse.ok();
    }


}
