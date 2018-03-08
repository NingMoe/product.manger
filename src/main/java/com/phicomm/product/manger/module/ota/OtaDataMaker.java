package com.phicomm.product.manger.module.ota;

import com.phicomm.product.manger.utils.CRC16Util;

/**
 * 构建数据
 *
 * @author wei.yang on 2018/3/8
 */
public class OtaDataMaker {

    private static final byte SECRET = 0x55;

    /**
     * 测试mac:60:01:94:65:8d:2d
     */
    private static byte[] testMac = new byte[]{
            0x60,
            0x01,
            (byte) 0x94,
            0x65,
            (byte) 0x8d,
            0x2d
    };

    /**
     * 开始字节
     */
    private static byte[] startByte = new byte[]{
            0x55,
            (byte) 0xaa
    };

    /**
     * 产品编码
     */
    private static byte[] productCode = new byte[]{
            0x00,
            0x00,
            0x00,
            0x01,
            0x00,
            0x00,
            0x00,
            0x01
    };

    /**
     * 目标mac
     */
    private static byte[] serverMac = new byte[]{
            (byte) 0xff,
            (byte) 0xff,
            (byte) 0xff,
            (byte) 0xff,
            (byte) 0xff,
            (byte) 0xff
    };

    /**
     * 保留字节
     */
    private static byte[] reservedData = new byte[]{
            0x00,
            0x00
    };

    /**
     * 获取基础数据
     *
     * @param data 要补充的数据
     */
    static void getCommonData(byte[] data) {
        System.arraycopy(startByte, 0, data, 0, 2);
        System.arraycopy(productCode, 0, data, 4, 8);
        System.arraycopy(testMac, 0, data, 12, 6);
        System.arraycopy(serverMac, 0, data, 18, 6);
        System.arraycopy(reservedData, 0, data, 24, 2);
        setDataLength(data, data.length);
    }

    /**
     * 设置数据长度
     *
     * @param data   数据
     * @param length 长度
     */
    private static void setDataLength(byte[] data, int length) {
        data[2] = (byte) ((length >>> 8) & 0xff);
        data[3] = (byte) (length & 0xff);
    }

    /**
     * 设置crc
     *
     * @param data 数据
     */
    static void setCrc(byte[] data) {
        int len = data.length;
        int crc16 = CRC16Util.calcCrc16(data, 2, len - 6);
        data[len - 4] = (byte) ((crc16 >>> 24) & 0xff);
        data[len - 3] = (byte) ((crc16 >>> 16) & 0xff);
        data[len - 2] = (byte) ((crc16 >> 8) & 0xff);
        data[len - 1] = (byte) (crc16 & 0xff);
        dataProcessing(data);
    }

    /**
     * 加密或解密
     *
     * @param data 数据
     */
    static void dataProcessing(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] ^= SECRET;
        }
    }

    /**
     * 设置指令
     *
     * @param data 数据
     * @param val  指令
     */
    static void setInstructions(byte[] data, short val) {
        data[26] = (byte) ((val >>> 8) & 0xff);
        data[27] = (byte) (val & 0xff);
    }

    /**
     * 获取关闭socket的数据
     *
     * @return 数据
     */
    static byte[] getCloseSocketData() {
        byte[] closeData = new byte[32];
        getCommonData(closeData);
        setInstructions(closeData, (short) 0x8052);
        setCrc(closeData);
        return closeData;
    }

    /**
     * 获取int值
     *
     * @param data   数据
     * @param offset 偏移量
     * @return 数字
     */
    @SuppressWarnings("all")
    static int toIntWith4Byte(byte[] data, int offset) {
        return (data[offset] & 0xff) << 24 |
                (data[offset + 1] & 0xff) << 16 |
                (data[offset + 2] & 0xff) << 8 |
                data[offset + 3] & 0xff;
    }
}
