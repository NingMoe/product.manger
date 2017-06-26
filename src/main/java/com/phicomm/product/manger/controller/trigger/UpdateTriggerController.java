package com.phicomm.product.manger.controller.trigger;

import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.service.OtaServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.HostAndPort;

import java.io.IOException;
import java.util.List;

/**
 * 触发Ota升级
 * Created by wei.yang on 2017/6/8.
 */
@Controller
@Api(value = "通知服务器更新缓存", description = "通知服务器去更新本地缓存")
public class UpdateTriggerController {

    private OtaServerService otaServerService;

    @Autowired
    public UpdateTriggerController(OtaServerService otaServerService) {
        this.otaServerService = otaServerService;
    }

    /**
     * 单纯的一个trigger操作
     *
     * @return 触发失败的主机
     * @throws IOException io异常
     */
    @RequestMapping(value = "balance/ota/trigger", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    public Response<List<HostAndPort>> updateTrigger() throws IOException {
        return new Response<List<HostAndPort>>().setData(otaServerService.updateTrigger());
    }
}
