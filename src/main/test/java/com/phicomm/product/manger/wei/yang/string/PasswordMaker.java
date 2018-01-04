package com.phicomm.product.manger.wei.yang.string;

import com.phicomm.product.manger.utils.MongoDbUtil;
import org.bson.Document;
import org.junit.Test;

/**
 * Created by wei.yang on 2017/10/11.
 */
public class PasswordMaker {

    @Test
    public void maker() {
        System.out.println(StringMaker.maker(16));
        Document time = MongoDbUtil.timeFormat("%Y-%m-%d", "timestamp");
        Document project = new Document("date", time)
                .append("platform", "$equipmentTerminalInfo.systemInfo.platform")
                .append("channel", "$equipmentTerminalInfo.appInfo.channel");
        System.out.println(project.toJson());
    }
}
