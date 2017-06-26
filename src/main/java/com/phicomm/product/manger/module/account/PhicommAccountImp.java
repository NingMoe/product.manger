package com.phicomm.product.manger.module.account;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.product.manger.config.SwaggerPropertiesConfig;
import com.phicomm.product.manger.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 统一账号接口
 * Created by yufei.liu on 2017/5/31.
 */
@Component
public class PhicommAccountImp implements PhicommAccount {

    private static final String AUTHORITY_CODE = "https://account.phicomm.com/v1/authorization";

    private static final String PHONE_LOGIN = "https://account.phicomm.com/v1/login";

    private static final String CLIENT_SECRET = "feixun*123";

    private static final String RESPONSE_TYPE = "code";

    private static final String CLIENT_ID = "52";

    private static final String SCOPE = "read";

    private String swaggerUsername;

    private String swaggerPassword;

    @Autowired
    public PhicommAccountImp(SwaggerPropertiesConfig propertiesConfig) {
        this.swaggerUsername = propertiesConfig.getSwaggerLoginName();
        this.swaggerPassword = propertiesConfig.getSwaggerLoginPassword();
        Assert.notNull(this.swaggerUsername);
        Assert.notNull(this.swaggerPassword);
    }

    /**
     * 获取授权码:暂不考虑返回状态码非0的情况
     */
    private static String getAuthCode() throws IOException {
        JSONObject params = new JSONObject();
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        params.put("response_type", RESPONSE_TYPE);
        params.put("scope", SCOPE);
        String response = HttpUtil.openUrl(AUTHORITY_CODE, "GET", params);
        JSONObject result = JSONObject.parseObject(response);
        return result.getString("authorizationcode");
    }

    /**
     * 登录
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @return 登陆结果：access_token、uid、refresh_token、refresh_token_expire、error（状态码）和access_token_expire
     * @throws Exception 访问异常
     */
    @Override
    public Map<String, String> login(String phoneNumber, String password) throws Exception {
        if (swaggerUsername.equals(phoneNumber) && swaggerPassword.equals(password)) {
            return new HashMap<>();
        } else {
            JSONObject params = new JSONObject();
            params.put("phonenumber", phoneNumber);
            params.put("password", getMD5(password));
            params.put("authorizationcode", getAuthCode());
            String response = HttpUtil.openUrl(PHONE_LOGIN, "POST", params);
            Map<String, String> result = getResult(response);
            if (!"0".equals(result.get("error"))) {
                return null;
            }
            return result;
        }
    }

    /**
     * 将字符串解析为Map<String,String>
     *
     * @param s 待解析的字符串
     */
    private Map<String, String> getResult(String s) {
        Map<String, String> result = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(s);
        Set<String> sets = jsonObject.keySet();
        for (String set : sets) {
            result.put(set, jsonObject.getString(set));
        }
        return result;
    }

    /**
     * 获取md5值
     */
    private String getMD5(String s) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] e = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(e);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }
            return new String(str);
        } catch (Exception var10) {
            return null;
        }
    }
}
