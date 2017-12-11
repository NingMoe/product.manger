package com.phicomm.product.manger.utils;

import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;

/**
 * @author wei.yang on 2017/12/7
 */
public class EnvironmentUtil {

    private static final Logger logger=Logger.getLogger(EnvironmentUtil.class);

    private static final String PROD = "prod";

    /**
     * 数据库选择
     *
     * @param environment 环境
     */
    public static void selectEnvironment(String environment) {
        switch (environment) {
            case PROD:
                logger.info("environment is Prod");
                CustomerContextHolder.selectProdDataSource();
                break;
            default:
                logger.info("environment is test");
                CustomerContextHolder.selectTestDataSource();
                break;
        }
    }
}
