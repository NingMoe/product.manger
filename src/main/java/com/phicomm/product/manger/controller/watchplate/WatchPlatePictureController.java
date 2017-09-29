package com.phicomm.product.manger.controller.watchplate;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.UploadFileException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.watchplate.WatchPlatePictureUpload;
import com.phicomm.product.manger.service.WatchPlatePictureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by xiang.zhang on 2017/9/6.
 */
@Controller
public class WatchPlatePictureController {

    private WatchPlatePictureService watchPlatePictureService;

    @Autowired
    public WatchPlatePictureController(WatchPlatePictureService watchPlatePictureService) {
        this.watchPlatePictureService = watchPlatePictureService;
        Assert.notNull(this.watchPlatePictureService);
    }

    /**
     * 表盘图片上传
     * @param file 表盘图片
     * @param picId 图片编号
     * @param picChiName 图片中文名
     * @param picEngName 图片英文名
     * @param picVersion 图片版本号
     * @param picResolution 图片分辨率
     * @param environment  应用环境
     * @return 图片上传成功
     * @throws DataFormatException 数据格式错误
     * @throws UploadFileException 上传失败
     */
    @RequestMapping(value = "watchplate/picture/upload/file", method = RequestMethod.POST)
    @ApiOperation("图片上传接口")
    @ResponseBody
    @FunctionPoint(value = "common")
    public CommonResponse pictureUpload(@RequestPart("file") MultipartFile[] file,
                                        @RequestParam("picId") int[] picId,
                                        @RequestParam("picChiName") String[] picChiName,
                                        @RequestParam("picEngName") String[] picEngName,
                                        @RequestParam("picVersion") String picVersion,
                                        @RequestParam("picResolution") String picResolution,
                                        @RequestParam("environment") String environment)
            throws DataFormatException, UploadFileException{
        watchPlatePictureService.pictureUploadNumber(file, picId, picChiName, picEngName, picVersion, picResolution, environment);
        return CommonResponse.ok();
    }

    /**
     * 获取表盘图片列表
     * @return 返回图片信息详情
     */
    @RequestMapping(value = "watchplate/picture/list/page", method = RequestMethod.POST)
    @ApiOperation("获取图片列表")
    @ResponseBody
    @FunctionPoint(value = "common")
    public Response<List<WatchPlatePictureUpload>> watchPlatePictureList() {
        List<WatchPlatePictureUpload> watchPlatePictureList = watchPlatePictureService.watchPlatePictureList();
        return new Response<List<WatchPlatePictureUpload>>().setData(watchPlatePictureList);
    }

    /**
     * 获取表盘配置信息
     * @return 返回配置信息
     */
    @RequestMapping(value = "watchplate/upload/config/get", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ApiOperation("获取配置信息")
    @FunctionPoint(value = "common")
    public CommonResponse getWatchPlateConfig() {
        String configuration = watchPlatePictureService.getWatchPlateConfig();
        return CommonResponse.ok().setDescription(configuration);
    }

    /**
     * 设置表盘配置信息
     * @param configuration 配置参数
     * @return 配置成功
     */
    @RequestMapping(value = "watchplate/upload/config/set", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ApiOperation("设置配置信息")
    @FunctionPoint(value = "common")
    public CommonResponse setWatchPlateConfig(@RequestParam("configuration") String configuration) {
        watchPlatePictureService.setWatchPlateConfig(configuration);
        return CommonResponse.ok();
    }
}
