package com.phicomm.product.manger.controller.share;

import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.service.ShareStatisticService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 分享数据controller
 *
 * @author song02.cao
 */
@Controller
public class ShareStatisticController {

    private ShareStatisticService shareStatisticService;

    @Autowired
    public ShareStatisticController(ShareStatisticService shareStatisticService) {
        this.shareStatisticService = shareStatisticService;
        Assert.notNull(this.shareStatisticService, "the shareStatisticService is null");
    }


    /**
     * 获取14天内的统计数据
     *
     * @return 返回统计数据
     */
    @RequestMapping(value = "share/statistic/14days", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取分享数据14天内统计情况")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, List<Long>>> getShareStatistic14Days() {
        return new Response<Map<String, List<Long>>>().setData(shareStatisticService.getShareStatistic14Days());
    }
}
