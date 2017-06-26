package com.phicomm.product.manger.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常处理
 * Created by yufei.liu on 2017/2/24.
 */
public class ExceptionUtil {

    private static final Logger logger = Logger.getLogger(ExceptionUtil.class);

    private ExceptionUtil() {
    }

    /**
     * 获取异常信息
     *
     * @param e 异常
     */
    public static String getErrorMessage(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    logger.info(e1);
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }

}
