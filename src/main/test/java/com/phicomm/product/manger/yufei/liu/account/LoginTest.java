package com.phicomm.product.manger.yufei.liu.account;

import com.phicomm.product.manger.dao.AdminUserInfoMapper;
import com.phicomm.product.manger.module.account.PhicommAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

/**
 * 登陆
 * Created by yufei.liu on 2017/5/31.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring-root.xml"})
public class LoginTest {

    @Autowired
    private PhicommAccount phicommAccount;

    @Autowired
    private AdminUserInfoMapper adminUserInfoMapper;

    @Test
    public void test() throws Exception {
        Map<String, String> map = phicommAccount.login("13311921091", "lyf123-+");
        System.out.println(map);
    }

    /**
     * 测试获取用户信息sql
     */
    @Test
    public void getUserInfoTest() {
        System.out.println(adminUserInfoMapper.getUserInfo("admin"));
        System.out.println(adminUserInfoMapper.getUserInfo("13311921091"));
    }

}
