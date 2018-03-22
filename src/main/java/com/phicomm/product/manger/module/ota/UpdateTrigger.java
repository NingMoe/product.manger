package com.phicomm.product.manger.module.ota;

import com.phicomm.product.manger.model.ota.BalanceOtaStatus;
import org.apache.log4j.Logger;
import redis.clients.jedis.HostAndPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

/**
 * 触发服务器，从而拉取最新版本
 *
 * @author wei.yang on 2017/6/8.
 */
public class UpdateTrigger {

    private static final Logger logger = Logger.getLogger(UpdateTrigger.class);

    private static final int DEFAULT_TIME_OUT = 3000;

    private static final int RESPONSE_DATA_LENGTH = 32;

    /**
     * socket trigger ,会返回无法连接的主机和能连接但是不能正确触发的主机；socket连接主机的超时时间设置成了3秒，
     * 超过3秒则认为该主机不属于可以触发升级的服务器
     * 注：按需进行ota更新触发，在此处并未将ota和mcu一起进行触发
     *
     * @param hostAndPort ip 和 port
     * @throws IOException io异常
     */
    public HostAndPort balanceUpdateTrigger(HostAndPort hostAndPort, BalanceOtaStatus balanceOtaStatus) throws IOException {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(hostAndPort.getHost(), hostAndPort.getPort()), DEFAULT_TIME_OUT);
        } catch (SocketTimeoutException | SocketException e) {
            return hostAndPort;
        }
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        byte[] triggerData = getSingleTriggerData(balanceOtaStatus);
        try {
            obtainResponse(inputStream, outputStream, triggerData);
        } catch (IOException e) {
            return hostAndPort;
        }
        byte[] closeData = OtaDataMaker.getCloseSocketData();
        obtainResponse(inputStream, outputStream, closeData);
        outputStream.close();
        inputStream.close();
        socket.close();
        return null;
    }

    /**
     * socket trigger ,会返回无法连接的主机和能连接但是不能正确触发的主机；socket连接主机的超时时间设置成了3秒，
     * 超过3秒则认为该主机不属于可以触发升级的服务器
     * 注：按需进行ota更新触发，在此处并未将ota和mcu一起进行触发
     *
     * @param hostAndPort ip 和 port
     * @throws IOException io异常
     */
    public HostAndPort triggerAll(HostAndPort hostAndPort) throws IOException {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(hostAndPort.getHost(), hostAndPort.getPort()), DEFAULT_TIME_OUT);
        } catch (SocketTimeoutException | SocketException e) {
            return hostAndPort;
        }
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        byte[] triggerData = getTriggerAllData();
        try {
            obtainResponse(inputStream, outputStream, triggerData);
        } catch (IOException e) {
            return hostAndPort;
        }
        obtainResponse(inputStream, outputStream, OtaDataMaker.getCloseSocketData());
        outputStream.close();
        inputStream.close();
        socket.close();
        return null;
    }

    /**
     * 处理数据
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param data         要发送的数据
     * @throws IOException io异常
     */
    private void obtainResponse(InputStream inputStream, OutputStream outputStream, byte[] data)
            throws IOException {
        outputStream.write(data);
        outputStream.flush();
        long startTime = System.currentTimeMillis();
        while (inputStream.available() < RESPONSE_DATA_LENGTH) {
            if (System.currentTimeMillis() - startTime > 10000) {
                throw new IOException();
            }
        }
        byte[] respond = new byte[RESPONSE_DATA_LENGTH];
        if (inputStream.read(respond) != RESPONSE_DATA_LENGTH) {
            throw new IOException();
        }
        logger.info(Arrays.toString(respond));
    }

    /**
     * 获取触发某个设备固件类型的数据
     *
     * @param balanceOtaStatus 固件信息
     * @return 触发数据
     */
    private byte[] getSingleTriggerData(BalanceOtaStatus balanceOtaStatus) {
        byte[] singleTrigger = new byte[48];
        OtaDataMaker.getCommonData(singleTrigger);
        OtaDataMaker.setInstructions(singleTrigger, (short) 0x6003);
        String firmwareType = balanceOtaStatus.getFirmwareType();
        byte abFlag;
        switch (firmwareType) {
            case "mcu":
                abFlag = 3;
                break;
            case "blu":
                abFlag = 4;
                break;
            default:
                abFlag = 1;
                break;
        }
        singleTrigger[11] = getProductionCode(balanceOtaStatus.getProduction());
        singleTrigger[28] = abFlag;
        OtaDataMaker.setCrc(singleTrigger);
        return singleTrigger;
    }

    /**
     * 获取触发某个设备固件类型的数据
     *
     * @return 触发数据
     */
    private byte[] getTriggerAllData() {
        byte[] singleTrigger = new byte[32];
        OtaDataMaker.getCommonData(singleTrigger);
        OtaDataMaker.setInstructions(singleTrigger, (short) 0x6002);
        singleTrigger[12] = getProductionCode("wifi");
        OtaDataMaker.setCrc(singleTrigger);
        OtaDataMaker.dataProcessing(singleTrigger);
        return singleTrigger;
    }

    /**
     * 获取产品编码的最后一位
     *
     * @param production 产品编码
     * @return 标志位
     */
    private byte getProductionCode(String production) {
        switch (production) {
            case "s7":
                return 1;
            case "s9":
                return 2;
            case "s7pe":
                return 3;
            default:
                return 0;
        }
    }

}
