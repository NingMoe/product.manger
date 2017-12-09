package com.phicomm.product.manger.utils;

import com.phicomm.product.manger.module.dds.CustomerContextHolder;

/**
 * @author wei.yang on 2017/12/7
 */
public class EnvironmentUtil {

    private static final String PROD = "prod";

    /**
     * 数据库选择
     *
     * @param environment 环境
     */
    public static void selectEnvironment(String environment) {
        switch (environment) {
            case PROD:
                CustomerContextHolder.selectProdDataSource();
                break;
            default:
                CustomerContextHolder.selectTestDataSource();
                break;
        }
    }
}
