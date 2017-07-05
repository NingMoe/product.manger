package com.phicomm.product.manger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能点，用于权限拦截
 * Created by yufei.liu on 2017/5/27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FunctionPoint {

    /**
     * 拥有的功能点（包含自己的functionPoint）
     */
    String[] value() default {};

    /**
     * 是否必须登陆才可以访问接口
     */
    boolean mustLogin() default true;

}
