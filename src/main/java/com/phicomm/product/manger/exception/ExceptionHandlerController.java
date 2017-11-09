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
    private static JSONObject exception9;
    private static JSONObject exception10;
    private static JSONObject exception11;
    private static JSONObject exception12;
    private static JSONObject exception13;
    private static JSONObject exception14;
    private static JSONObject exception15;
    private static JSONObject exception16;
    private static JSONObject exception17;
    private static JSONObject exception18;
    private static JSONObject exception19;
    private static JSONObject exception20;
    private static JSONObject exception21;
    private static JSONObject exception22;
    private static JSONObject exception23;

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

    static {
        exception9 = new JSONObject();
        exception9.put("status", 9);
        exception9.put("description", "version not exist.");
    }

    static {
        exception10 = new JSONObject();
        exception10.put("status", 10);
        exception10.put("description", "Type not found.");
    }

    static {
        exception11 = new JSONObject();
        exception11.put("status", 11);
        exception11.put("description", "firmware disable.");
    }

    static {
        exception12 = new JSONObject();
        exception12.put("status", 12);
        exception12.put("description", "firmware current enable.");
    }

    static {
        exception13 = new JSONObject();
        exception13.put("status", 13);
        exception13.put("description", "Swagger project not found .");
    }

    static {
        exception14 = new JSONObject();
        exception14.put("status", 14);
        exception14.put("description", "Swagger project already existed .");
    }

    static {
        exception15 = new JSONObject();
        exception15.put("status", 15);
        exception15.put("description", "user has existed.");
    }

    static {
        exception16 = new JSONObject();
        exception16.put("status", 16);
        exception16.put("description", "permission has not enough exception.");
    }

    static {
        exception17 = new JSONObject();
        exception17.put("status", 17);
        exception17.put("description", "user not found.");
    }

    static {
        exception18 = new JSONObject();
        exception18.put("status", 18);
        exception18.put("description", "id has exist.");
    }

    static {
        exception19 = new JSONObject();
        exception19.put("status", 19);
        exception19.put("description", "firmware trigger failed.");
    }

    static {
        exception20 = new JSONObject();
        exception20.put("status", 20);
        exception20.put("description", "feedback message is empty.");
    }

    static {
        exception21 = new JSONObject();
        exception21.put("status", 21);
        exception21.put("description", "feedback lock error.");
    }

    static {
        exception22 = new JSONObject();
        exception22.put("status", 22);
        exception22.put("description", "feedback not found.");
    }

    static {
        exception23 = new JSONObject();
        exception23.put("status", 23);
        exception23.put("description", "feedback upload failure.");
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
     * 版本已经存在
     */
    @ExceptionHandler(VersionHasExistException.class)
    public ModelAndView processVersionHasExistException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception8);
    }

    /**
     * 版本不存在
     */
    @ExceptionHandler(VersionNotExistException.class)
    public ModelAndView processVersionNotExistException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception9);
    }

    /**
     * 类型错误
     */
    @ExceptionHandler(TypeNotPresentException.class)
    public ModelAndView processTypeNotPresentException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception10);
    }

    /**
     * 当前固件不可用
     */
    @ExceptionHandler(FirmwareDisableException.class)
    public ModelAndView processFirmwareDisableException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception11);
    }

    /**
     * 当前固件可用
     */
    @ExceptionHandler(FirmwareEnableException.class)
    public ModelAndView processFirmwareEnableException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception12);
    }

    /**
     * 某个项目不存在
     */
    @ExceptionHandler(SwaggerProjectNotFoundException.class)
    public ModelAndView processSwaggerProjectNotFoundException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception13);
    }

    /**
     * 某个项目已经存在
     */
    @ExceptionHandler(SwaggerProjectHasExistedException.class)
    public ModelAndView processSwaggerProjectHasExistedException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception14);
    }

    /**
     * 用户已经存在
     */
    @ExceptionHandler(UserHasExistException.class)
    public ModelAndView processUserHasExistException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception15);
    }

    /**
     * 权限不够
     */
    @ExceptionHandler(PermissionHasNotEnoughException.class)
    public ModelAndView processPermissionHasNotEnoughException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception16);
    }

    /**
     * 用户找不到
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView processUserNotFoundException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception17);
    }

    /**
     * ID已经存在
     */
    @ExceptionHandler(IdHasExistException.class)
    public ModelAndView processIdHasExistException(){
        return new ModelAndView(new MappingJackson2JsonView(),exception18);
    }

    /**
     * 固件触发失败
     */
    @ExceptionHandler(FirmwareTriggerFailException.class)
    public ModelAndView processFirmwareTriggerFailException(){
        return new ModelAndView(new MappingJackson2JsonView(),exception19);
    }
    /**
     * 反馈为空
     */
    @ExceptionHandler(FeedbackEmptyException.class)
    public ModelAndView processFeedbackEmptyException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception20);
    }

    /**
     * 反馈回复锁定
     */
    @ExceptionHandler(FeedbackLockException.class)
    public ModelAndView processFeedbackLockException() {
        return new ModelAndView(new MappingJackson2JsonView(), exception21);
    }

    /**
     * 反馈不存在
     */
    @ExceptionHandler(FeedbackNotFoundException.class)
    public ModelAndView processFeedbackNotFoundException(){
        return new ModelAndView(new MappingJackson2JsonView(),exception22);
    }

    /**
     * 反馈上传失败
     */
    @ExceptionHandler(FeedbackUploadException.class)
    public ModelAndView processFeedbackUploadException(){
        return new ModelAndView(new MappingJackson2JsonView(),exception23);
    }
}
