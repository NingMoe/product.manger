package com.phicomm.product.manger.controller.balance.server;

import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.ServerAddressExistException;
import com.phicomm.product.manger.exception.ServerAddressNotExistException;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.server.BalanceServerAddressBean;
import com.phicomm.product.manger.service.OtaServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.HostAndPort;

import java.util.List;

/**
 * 需要通知的Ota服务器地址
 * Created by wei.yang on 2017/6/8.
 */
@Controller
@Api(value = "触发Ota固件更新的地址操作", description = "Ota固件升级的地址操作")
public class OtaServerController {

    private OtaServerService otaServerService;

    @Autowired
    public OtaServerController(OtaServerService otaServerService) {
        this.otaServerService = otaServerService;
    }

    /**
     * 录入ota升级需要通知到的地址
     *
     * @param serverIp   serverIp
     * @param serverPort 端口号
     * @return Response.class
     * @throws ServerAddressExistException 录入失败
     */
    @RequestMapping(value = "balance/server/address/insert", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 4, message = "录入失败", response = ServerAddressExistException.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = DataFormatException.class)
    })
    public Response<Object> insertServerAddress(@RequestParam("serverIp") String serverIp,
                                                @RequestParam("serverPort") int serverPort)
            throws DataFormatException, ServerAddressExistException {
        otaServerService.insertServerAddress(new HostAndPort(serverIp, serverPort));
        return new Response<>().setData("HostAndPort:" + new HostAndPort(serverIp, serverPort).toString());
    }

    /**
     * 删除服务器地址
     *
     * @param ip   ip
     * @param port 端口号
     * @return Response.class
     * @throws ServerAddressNotExistException 删除失败
     * @throws DataFormatException            数据格式异常
     */
    @RequestMapping(value = "balance/server/address/delete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = DataFormatException.class),
            @ApiResponse(code = 5, message = "删除失败，可能地址不存在", response = ServerAddressNotExistException.class)
    })
    public Response<Object> deleteServerAddress(@RequestParam("ip") String ip,
                                                @RequestParam("port") int port)
            throws DataFormatException, ServerAddressNotExistException {
        otaServerService.deleteServerAddress(new HostAndPort(ip, port));
        return new Response<>().setData("HostAndPort:" + new HostAndPort(ip, port).toString());
    }

    /**
     * 删除服务器地址
     *
     * @return Response.class
     */
    @RequestMapping(value = "balance/server/address/obtain", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = DataFormatException.class),
            @ApiResponse(code = 5, message = "删除失败，可能地址不存在", response = ServerAddressNotExistException.class)
    })
    public Response<List<BalanceServerAddressBean>> obtainServerAddress()
            throws DataFormatException, ServerAddressNotExistException {

        return new Response<List<BalanceServerAddressBean>>().setData(otaServerService.obtainHostAndPort());
    }
}
