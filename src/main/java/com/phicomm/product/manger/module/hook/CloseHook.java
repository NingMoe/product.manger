package com.phicomm.product.manger.module.hook;

import com.phicomm.product.manger.utils.ExceptionUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * 容器关闭时候比较有用
 * Created by yufei.liu on 2016/10/4.
 */
public class CloseHook implements ServletContextListener {

    private final Logger logger = Logger.getLogger(getClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // do nothing
    }

    /**
     * 项目关闭会自动回调这个方法
     *
     * @param sce servlet context
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error(ExceptionUtil.getErrorMessage(e));
            }
        }
    }
}
