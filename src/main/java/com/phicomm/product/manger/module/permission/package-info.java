/**
 * Filter说明：
 * 一共存在三个拦截器：
 * 1、SwaggerUIFilter采用servlet拦截器，它拦截swagger相关页面，对其他接口没有影响
 * 2、loginFilter拦截没有登陆的请求，当然针对静态资源和开放接口不做拦截
 * 3、PermissionFilter拦截没有合法权限的请求
 *
 * 本项目的权限管理的粒度到接口
 *
 * Created by yufei.liu on 2017/5/31.
 */
package com.phicomm.product.manger.module.permission;