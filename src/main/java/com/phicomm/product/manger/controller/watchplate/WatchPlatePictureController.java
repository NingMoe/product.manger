package com.phicomm.product.manger.controller.watchplate;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.watchplate.WatchPlatePictureDeleteBean;
import com.phicomm.product.manger.model.watchplate.WatchPlatePictureUpload;
import com.phicomm.product.manger.service.WatchPlatePictureService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by xiang.zhang on 2017/9/6.
 * @author xiang.zhang
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
     * @param file          表盘图片
     * @param picId         图片编号
     * @param picChiName    图片中文名
     * @param picEngName    图片英文名
     * @param picVersion    图片版本号
     * @param picResolution 图片分辨率
     * @param environment   应用环境
     * @return 图片上传成功
     */
    @RequestMapping(value = "watchplate/picture/upload/file", method = RequestMethod.POST)
    @ApiOperation("图片上传接口")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public CommonResponse pictureUpload(@RequestPart("file") MultipartFile[] file,
                                        @RequestParam("picId") Integer[] picId,
                                        @RequestParam("picChiName") String[] picChiName,
                                        @RequestParam("picEngName") String[] picEngName,
                                        @RequestParam("picVersion") String picVersion,
                                        @RequestParam("picResolution") String[] picResolution,
                                        @RequestParam("environment") String environment)
            throws IOException {
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
    public Response<List<WatchPlatePictureUpload>> watchPlatePictureList(@RequestParam("environment") String environment)
    throws IOException{
        List<WatchPlatePictureUpload> watchPlatePictureList = watchPlatePictureService.watchPlatePictureList(environment);
        return new Response<List<WatchPlatePictureUpload>>().setData(watchPlatePictureList);
    }

    /**
     * 删除表盘信息
     * @param
     */
    @RequestMapping(value = "watchplate/picture/list/delete", method = RequestMethod.POST)
    @ApiOperation("删除图片信息")
    @ResponseBody
    @FunctionPoint(value = "common")
    public CommonResponse watchPlatePictureListDelete(@RequestBody WatchPlatePictureDeleteBean data)
            throws IOException{
        System.out.println("delete图片列表"+data);
        if(data.getData().size()<=0){
            return CommonResponse.error();
        }
        watchPlatePictureService.pictureListDelete(data);
        return CommonResponse.ok();
    }

    @RequestMapping(value = "watchplate/picture/version/update", method = RequestMethod.POST)
    @ApiOperation("图片版本更新接口")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public CommonResponse pictureUpload(@RequestParam("picOldVersion") String picOldVersion,
                                        @RequestParam("picNewVersion") String picNewVersion,
                                        @RequestParam("environment") String environment)
            throws IOException {
        watchPlatePictureService.pictureVersionUpdate(picOldVersion,picNewVersion,environment);
        return CommonResponse.ok();
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
     *
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
