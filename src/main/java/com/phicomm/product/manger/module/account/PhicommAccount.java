package com.phicomm.product.manger.module.account;

import java.util.Map;

/**
 * 账号接口
 * Created by yufei.liu on 2017/5/31.
 */
public interface PhicommAccount {

    /**
     * 登陆，用户密码验证
     * 1、手机号已经验证过格式，不需要再次验证
     * 2、如果返回null表示用户密码错误不允许登陆
     * 3、当phoneNumber是admin,则验证密码是否是swagger密码
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @return 是否可以登陆以及用户信息
     */
    Map<String, String> login(String phoneNumber, String password) throws Exception;

}
