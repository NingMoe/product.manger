package com.phicomm.product.manger.wei.yang.string;

import com.phicomm.product.manger.utils.CRC16Util;
import com.phicomm.product.manger.utils.MongoDbUtil;
import org.bson.Document;
import org.junit.Test;

import java.io.File;

/**
 * Created by wei.yang on 2017/10/11.
 */
public class PasswordMaker {

    @Test
    public void maker() {
        System.out.println(StringMaker.maker(32).toLowerCase());
        Document time = MongoDbUtil.timeFormat("%Y-%m-%d", "timestamp");
        Document project = new Document("date", time)
                .append("platform", "$equipmentTerminalInfo.systemInfo.platform")
                .append("channel", "$equipmentTerminalInfo.appInfo.channel");
        System.out.println(project.toJson());
    }

    @Test
    public void test(){
        System.out.println(CRC16Util.calcFileCrc16(new File("D:\\Google\\wKgoJlppjBmEGqZAAAAAAAAAAAA272.bin")));
    }
}
