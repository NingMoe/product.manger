package com.phicomm.product.manger.service;

import com.google.common.collect.Lists;
import com.phicomm.product.manger.dao.OtaServerAddressMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.ServerAddressExistException;
import com.phicomm.product.manger.exception.ServerAddressNotExistException;
import com.phicomm.product.manger.model.ota.BalanceOtaStatus;
import com.phicomm.product.manger.model.server.BalanceOtaServerDetailBean;
import com.phicomm.product.manger.model.server.BalanceServerAddressBean;
import com.phicomm.product.manger.model.server.BalanceServerBean;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import com.phicomm.product.manger.module.ota.UpdateTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import sun.net.util.IPAddressUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 处理Ota服务器地址
 *
 * @author wei.yang on 2017/6/8.
 */
@Service
public class OtaServerService {

    private OtaServerAddressMapper otaServerAddressMapper;

    @Autowired
    public OtaServerService(OtaServerAddressMapper otaServerAddressMapper) {
        this.otaServerAddressMapper = otaServerAddressMapper;
    }

    /**
     * 触发Ota升级:product-manager
     *
     * @return 触发失败的主机
     * @throws IOException IO异常
     */
    public List<HostAndPort> updateTrigger(BalanceOtaStatus balanceOtaStatus) throws IOException {
        List<HostAndPort> hostAndPortList = new ArrayList<>();
        List<BalanceServerAddressBean> addressBeans = Lists.newArrayList();
        HostAndPort hostAndPort;
        /*addressBeans = otaServerAddressMapper.obtainServerAddress();*/
        BalanceServerAddressBean addressBean = new BalanceServerAddressBean();
        addressBean.setHostAndPort("114.141.173.27:16816");
        addressBeans.add(addressBean);
        if (addressBeans.isEmpty()) {
            return new ArrayList<>();
        }
        UpdateTrigger trigger = new UpdateTrigger();
        for (BalanceServerAddressBean balanceServerAddressBean : addressBeans) {
            hostAndPort = trigger.balanceUpdateTrigger(HostAndPort.parseString(balanceServerAddressBean.getHostAndPort()),
                    balanceOtaStatus);
            if (hostAndPort != null) {
                hostAndPortList.add(hostAndPort);
            }
        }
        CustomerContextHolder.clearDataSource();
        return hostAndPortList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    /**
     * 全部刷新
     *
     * @return 触发失败的主机
     * @throws IOException io异常
     */
    public List<HostAndPort> triggerAll() throws IOException {
        List<HostAndPort> hostAndPortList = new ArrayList<>();
        List<BalanceServerAddressBean> addressBeans;
        HostAndPort hostAndPort;
        addressBeans = otaServerAddressMapper.obtainServerAddress();
        if (addressBeans.isEmpty()) {
            return new ArrayList<>();
        }
        UpdateTrigger trigger = new UpdateTrigger();
        for (BalanceServerAddressBean balanceServerAddressBean : addressBeans) {
            hostAndPort = trigger.triggerAll(HostAndPort.parseString(balanceServerAddressBean.getHostAndPort()));
            if (hostAndPort != null) {
                hostAndPortList.add(hostAndPort);
            }
        }
        CustomerContextHolder.clearDataSource();
        return hostAndPortList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    /**
     * 录入ip
     *
     * @param hostAndPort ip 和 port
     * @throws ServerAddressExistException 地址录入异常：地址已经存在
     * @throws DataFormatException         数据格式异常
     */
    public void insertServerAddress(HostAndPort hostAndPort) throws DataFormatException, ServerAddressExistException {
        checkParamFormat(hostAndPort);
        int success = otaServerAddressMapper.insertServerAddress(hostAndPort.toString());
        if (success != 1) {
            throw new ServerAddressExistException();
        }
    }

    /**
     * 获取服务器地址
     *
     * @return 服务器地址
     */
    public List<BalanceServerAddressBean> obtainHostAndPort() {
        return otaServerAddressMapper.obtainServerAddress();
    }

    /**
     * 返回格式化好的数据
     *
     * @return 服务器地址信息
     */
    public List<BalanceOtaServerDetailBean> obtainServerList() {
        List<BalanceServerBean> balanceServerBeans = otaServerAddressMapper.obtainServerList();
        List<BalanceOtaServerDetailBean> balanceOtaServerDetailBeans = new ArrayList<>();
        BalanceOtaServerDetailBean balanceOtaServerDetailBean;
        HostAndPort hostAndPort;
        for (BalanceServerBean balanceServerBean : balanceServerBeans) {
            balanceOtaServerDetailBean = new BalanceOtaServerDetailBean();
            hostAndPort = HostAndPort.parseString(balanceServerBean.getHostAndPort());
            balanceOtaServerDetailBean.setCreateTime(balanceServerBean.getCreateTime());
            balanceOtaServerDetailBean.setIp(hostAndPort.getHost());
            balanceOtaServerDetailBean.setPort(hostAndPort.getPort());
            balanceOtaServerDetailBean.setId(balanceServerBean.getId());
            balanceOtaServerDetailBeans.add(balanceOtaServerDetailBean);
        }
        return balanceOtaServerDetailBeans;
    }

    /**
     * 删除ip
     *
     * @param hostAndPort ip 和 port
     * @throws ServerAddressNotExistException 删除失败:地址不存在
     * @throws DataFormatException            数据格式异常
     */
    public void deleteServerAddress(HostAndPort hostAndPort) throws DataFormatException, ServerAddressNotExistException {
        checkParamFormat(hostAndPort);
        int success = otaServerAddressMapper.deleteServerAddress(hostAndPort.toString());
        if (success < 1) {
            throw new ServerAddressNotExistException();
        }
    }

    /**
     * 检查参数的格式
     *
     * @param hostAndPort ip 和 port
     * @throws DataFormatException 数据格式异常
     */
    private void checkParamFormat(HostAndPort hostAndPort) throws DataFormatException {
        if (!IPAddressUtil.isIPv4LiteralAddress(hostAndPort.getHost())) {
            throw new DataFormatException();
        }
        int port = hostAndPort.getPort();
        if (port >= 65535 || port <= 0) {
            throw new DataFormatException();
        }
    }
}
