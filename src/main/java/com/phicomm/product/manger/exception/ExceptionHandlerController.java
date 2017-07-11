package com.phicomm.product.manger.exception;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.product.manger.utils.ExceptionUtil;
import org.apache.log4j.Logger;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 全局异常处理
 * Created by yufei.liu on 2016/11/15.
 */
@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger logger = Logger.getLogger(ExceptionHandlerController.class);

    private static JSONObject exception1001;
    private static JSONObject exception1;
    private static JSONObject exception2;
    private static JSONObject exception3;
    private static JSONObject exception4;
    private static JSONObject exception5;
    private static JSONObject exception6;
    private static JSONObject exception7;
    private static JSONObject exception8;

    static {
        exception1001 = new JSONObject();
        exception1001.put("status", 1001);
        exception1001.put("description", "yufei.liu not exception.");
    }

    static {
        exception1 = new JSONObject();
        exception1.put("status", 1);
        exception1.put("description", "http request method not supported.");
    }

    static {
        exception2 = new JSONObject();
        exception2.put("status", 2);
        exception2.put("description", "data format error.");
    }

    static {
        exception3 = new JSONObject();
        exception3.put("status", 3);
        exception3.put("description", "phicomm account occur exception.");
    }

    static {
        exception4 = new JSONObject();
        exception4.put("status", 4);
        exception4.put("description", "permission is empty.");
    }

    static {
        exception5 = new JSONObject();
        exception5.put("status", 5);
        exception5.put("description", "Server address already exist.");
    }

    static {
        exception6 = new JSONObject();
        exception6.put("status", 6);
        exception6.put("description", "Server address not exist.");
    }

    static {
        exception7 = new JSONObject();
        exception7.put("status", 7);
        exception7.put("description", "File upload failure.");
    }

    static {
        exception8 = new JSONObject();
        exception8.put("status", 8);
        exception8.put("description", "version has exist.");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView processException(Exception e) {
        logger.info(ExceptionUtil.getErrorMessage(e));
        return new ModelAndView(new MappingJackson2JsonView(), exception1001);
    }

    /**
     * http请求的method不正确
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView processHttpRequestMethodNotSupportedException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception1);
    }

    /**
     * 数据格式不正确
     */
    @ExceptionHandler(DataFormatException.class)
    public ModelAndView processDataFormatException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception2);
    }

    /**
     * 登陆统一账号失败
     */
    @ExceptionHandler(LoginException.class)
    public ModelAndView processLoginException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception3);
    }

    /**
     * 授权列表为空
     */
    @ExceptionHandler(PermissionEmptyException.class)
    public ModelAndView processPermissionEmptyException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception4);
    }

    /**
     * 地址已经存在
     */
    @ExceptionHandler(ServerAddressExistException.class)
    public ModelAndView processServerAddressExistException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception5);
    }

    /**
     * 地址不存在
     */
    @ExceptionHandler(ServerAddressNotExistException.class)
    public ModelAndView processServerAddressNotExistException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception6);
    }

    /**
     * 文件上传失败
     */
    @ExceptionHandler(UploadFileException.class)
    public ModelAndView processUploadFileException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception7);
    }

    /**
     * 版本已经存在的异常
     */
    @ExceptionHandler(VersionHasExistException.class)
    public ModelAndView processVersionHasExistException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception8);
    }
}
