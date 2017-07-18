package com.phicomm.product.manger.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.io.Resources;
import com.phicomm.product.manger.exception.UploadFileException;
import com.phicomm.product.manger.model.common.CommonResponse;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * 文件工具类
 * <p>
 * Created by yufei.liu on 2017/7/5.
 */
public class FileUtil {

    private static final Logger logger = Logger.getLogger(FileUtil.class);

    private static final String HERMES_FILE_HTTP_URL_PREFIX = "http://114.141.173.17:2580/hermes/file/";

    /**
     * hermes文件上传的接口
     */
    private static String HERMES_UPLOAD_URL;

    static {
        String file = Resources.getResource("hermes.properties").getFile();
        try {
            PropertiesConfiguration configuration = new PropertiesConfiguration(file);
            HERMES_UPLOAD_URL = configuration.getString("hermes.upload.url");
        } catch (ConfigurationException e) {
            throw new Error();
        }
        logger.info(String.format("hermes upload address is %s.", HERMES_UPLOAD_URL));
    }

    private static String hermesTempDirPath = System.getProperty("java.io.tmpdir");

    private FileUtil() {
    }

    public static String md5(File file) {
        return hash(file, "MD5");
    }

    public static String sha256(File file) {
        return hash(file, "SHA-256");
    }

    /**
     * 计算文件的MD5
     */
    private static String hash(File file, String algorithm) {
        if (!file.exists()) {
            return null;
        }
        FileInputStream fis = null;
        BigInteger bigInt;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            bigInt = new BigInteger(1, md.digest());
        } catch (Exception e) {
            logger.info(ExceptionUtil.getErrorMessage(e));
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.info(ExceptionUtil.getErrorMessage(e));
                }
            }
        }
        return bigInt.toString(16);
    }

    /**
     * 上传hermes，不使用retrofit框架
     *
     * @param file 本地文件
     */
    public static CommonResponse uploadHermes(File file, String fileName) throws IOException {
        String end = "\r\n";
        String hyphens = "--";
        String boundary = "-----------------" + UUID.randomUUID().toString().replace("-", "");
        HttpURLConnection httpURLConnection = null;
        CommonResponse commonResponse = null;
        DataOutputStream dataOutputStream = null;
        FileInputStream fileInputStream = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(HERMES_UPLOAD_URL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            httpURLConnection.setRequestProperty("hermes_downloadName", fileName);
            httpURLConnection.setRequestProperty("downloadName", fileName);
            httpURLConnection.setChunkedStreamingMode(8192);
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.connect();
            dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(hyphens + boundary + end);
            dataOutputStream.writeBytes(String.format("Content-Disposition: form-data; name=\"file\"; filename=\"%s\"", fileName) + end);
            dataOutputStream.writeBytes("Content-Type: application/octet-stream" + end);
            dataOutputStream.writeBytes(end);
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[2048];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, length);
            }
            dataOutputStream.writeBytes(end);
            dataOutputStream.writeBytes(hyphens + boundary + hyphens + end);
            dataOutputStream.flush();
            dataOutputStream.close();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                int ch;
                StringBuilder stringBuilder = new StringBuilder();
                while ((ch = inputStream.read()) != -1) {
                    stringBuilder.append((char) ch);
                }
                commonResponse = JSON.parseObject(stringBuilder.toString(), CommonResponse.class);
            }
        } catch (Exception e) {
            logger.info(ExceptionUtil.getErrorMessage(e));
            throw new IOException();
        } finally {
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return commonResponse;
    }

    /**
     * 上传文件到hermes
     *
     * @param file 文件
     * @return 下载链接
     * @throws UploadFileException 上传文件失败的链接
     */
    public static String uploadFileToHermes(MultipartFile file) throws UploadFileException {
        File tempFile = new File(hermesTempDirPath, UUID.randomUUID().toString());
        CommonResponse commonResponse = null;
        try {
            file.transferTo(tempFile);
            commonResponse = uploadHermes(tempFile, file.getOriginalFilename());
        } catch (IOException e) {
            logger.info(ExceptionUtil.getErrorMessage(e));
            throw new UploadFileException();
        } finally {
            //noinspection ResultOfMethodCallIgnored
            tempFile.delete();
        }
        return HERMES_FILE_HTTP_URL_PREFIX + commonResponse.getDescription();
    }
}
