package com.phicomm.product.manger.module.ota;

import com.phicomm.product.manger.utils.CRC16Util;
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
 * Created by wei.yang on 2017/6/8.
 */
public class UpdateTrigger {

    private static final byte[] CLOSE_RESPONSE = new byte[]{(byte) 0xC0, (byte) 0x52};

    private static final byte[] TRIGGER_DATA = new byte[]{(byte) 0xE0, 0};

    private static final Logger logger=Logger.getLogger(UpdateTrigger.class);

    /**
     * socket trigger ,会返回无法连接的主机和能连接但是不能正确触发的主机；socket连接主机的超时时间设置成了3秒，
     * 超过3秒则认为该主机不属于可以触发升级的服务器
     *
     * @param hostAndPort ip 和 port
     * @throws IOException io异常
     */
    public HostAndPort balanceUpdateTrigger(HostAndPort hostAndPort) throws IOException {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(hostAndPort.getHost(), hostAndPort.getPort()), 3000);
        } catch (SocketTimeoutException | SocketException e) {
            return hostAndPort;
        }
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        byte[] triggerResponse = obtainResponse(inputStream, outputStream, obtainTriggerData());
        if (!Arrays.equals(TRIGGER_DATA, triggerResponse)) {
            return hostAndPort;
        }
        byte[] closeResponse = obtainResponse(inputStream, outputStream, obtainCloseData());
        if (!Arrays.equals(CLOSE_RESPONSE, closeResponse)) {
            logger.info(Arrays.toString(closeResponse));
            throw new IOException();
        }

        if (outputStream != null) {
            outputStream.flush();
            outputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        socket.close();
        return null;
    }

    /**
     * 处理数据
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param data         要发送的数据
     * @return 命令集
     * @throws IOException io异常
     */
    @org.jetbrains.annotations.NotNull
    private byte[] obtainResponse(InputStream inputStream, OutputStream outputStream, byte[] data)
            throws IOException {
        outputStream.write(data);
        long startTime = System.currentTimeMillis();
        while (inputStream.available() < 32) {
            if (System.currentTimeMillis() - startTime > 10000) {
                throw new IOException();
            }
        }
        byte[] respond = new byte[32];
        if (inputStream.read(respond) != 32) {
            throw new IOException();
        }
        for (int i = 0; i < respond.length; i++) {
            respond[i] ^= 0x55;
        }
        return Arrays.copyOfRange(respond, 26, 28);
    }

    /**
     * 获取frame
     *
     * @return 数据帧
     */
    private byte[] obtainTriggerData() {
        byte[] data = new byte[32];
        byte[] frameHeader = new byte[]{(byte) 0x55, (byte) 0xAA, 0, 32, (byte) 0X01, (byte) 0X01, (byte) 0X01, (byte) 0X01,
                (byte) 0X01, (byte) 0X01, (byte) 0X01, (byte) 0X01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,
                (byte) 0x05, (byte) 0x06, 0, 0, (byte) 0x60, (byte) 0x00};
        System.arraycopy(frameHeader, 0, data, 0, frameHeader.length);
        int crc = CRC16Util.calcCrc16(data, 2, data.length - 6);
        data[data.length - 4] = (byte) (crc >>> 24 & 0xff);
        data[data.length - 3] = (byte) (crc >>> 16 & 0xff);
        data[data.length - 2] = (byte) (crc >>> 8 & 0xff);
        data[data.length - 1] = (byte) (crc & 0xff);
        for (int i = 0; i < data.length; i++) {
            data[i] ^= 0x55;
        }
        return data;
    }

    /**
     * 关闭socket
     *
     * @return 数据
     */
    private byte[] obtainCloseData() {
        byte[] data = new byte[32];
        byte[] temp = new byte[]{(byte) 0x55, (byte) 0xaa, 0, 32, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01,
                0x02, 0x03, 0x04, 0x05, 0x06, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff, 0, 0, (byte) 0x80, (byte) 0x52};
        System.arraycopy(temp, 0, data, 0, temp.length);
        int crc = CRC16Util.calcCrc16(data, 2, data.length - 6);
        data[data.length - 4] = (byte) (crc >>> 24 & 0xff);
        data[data.length - 3] = (byte) (crc >>> 16 & 0xff);
        data[data.length - 2] = (byte) (crc >>> 8 & 0xff);
        data[data.length - 1] = (byte) (crc & 0xff);
        for (int i = 0; i < data.length; i++) {
            data[i] ^= 0x55;
        }
        return data;
    }
}
