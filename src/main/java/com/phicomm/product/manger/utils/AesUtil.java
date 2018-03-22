package com.phicomm.product.manger.utils;

import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES加密解密
 *
 * @author qiang.ren
 * @date 2018/3/19
 */
public class AesUtil {

    private static final Logger logger = Logger.getLogger(AesUtil.class);

    private static final String AES = "AES";

    private static final String SECRET_KEY = "123456";

    /**
     * 生成秘钥
     *
     * @return 秘钥
     */
    private static SecretKey geneKey() {
        KeyGenerator keyGenerator;
        SecretKey secretKey = null;
        try {
            keyGenerator = KeyGenerator.getInstance(AES);
            SecureRandom random = new SecureRandom();
            random.setSeed(SECRET_KEY.getBytes());
            keyGenerator.init(random);
            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            logger.info(e.getMessage(), e);
        }
        return secretKey;
    }

    /**
     * 加密
     *
     * @param s 待加密字符串
     * @return 加密后的结果
     */
    public static String encrypt(String s) {
        String base64Result = null;
        try {
            Cipher cipher = Cipher.getInstance(AES);
            SecretKey secretKey = geneKey();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            cipher.update(s.getBytes());
            byte[] result = cipher.doFinal();
            base64Result = Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return base64Result;
    }

    /**
     * 解密
     *
     * @param s 待解密字符串
     * @return 解密后的结果
     */
    public static String decrpyt(String s) {
        String recoverResult = null;
        try {
            Cipher cipher = Cipher.getInstance(AES);
            SecretKey secretKey = geneKey();
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encodedBytes = Base64.getDecoder().decode(s.getBytes());
            byte[] result = cipher.doFinal(encodedBytes);
            recoverResult = new String(result);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return recoverResult;
    }
}
