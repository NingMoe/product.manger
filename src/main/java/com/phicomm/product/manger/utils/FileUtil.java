package com.phicomm.product.manger.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.phicomm.product.manger.exception.UploadFileException;
import com.phicomm.product.manger.model.common.CommonResponse;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 文件工具类
 * <p>
 * Created by yufei.liu on 2017/7/5.
 */
public class FileUtil {

    private static final Logger logger = Logger.getLogger(FileUtil.class);

    private static final String HERMES_FILE_HTTP_URL_PREFIX = "https://ihome.phicomm.com:2580/hermes/file/";

    private static final String ZIP = ".zip";

    private static byte[] buffer = new byte[1024];

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
        return String.format("%32s", bigInt.toString(16)).replaceAll(" ", "0");
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
    public static Map<String, String> uploadFileToHermes(MultipartFile file) throws UploadFileException {
        if (file.isEmpty()){
            return Maps.newHashMap();
        }
        Map<String, String> result = Maps.newHashMap();
        File tempFile = new File(hermesTempDirPath, UUID.randomUUID().toString());
        CommonResponse commonResponse;
        String description;
        try {
            file.transferTo(tempFile);
            String md5 = md5(tempFile);
            result.put("md5", md5);
            commonResponse = uploadHermes(tempFile, file.getOriginalFilename());
            description = commonResponse.getDescription();
        } catch (IOException e) {
            logger.info(ExceptionUtil.getErrorMessage(e));
            throw new UploadFileException();
        } finally {
            tempFile.delete();
        }
        if (!Strings.isNullOrEmpty(description)) {
            String url = HERMES_FILE_HTTP_URL_PREFIX + description;
            result.put("url", url);
        } else {
            logger.info("description is null");
            throw new UploadFileException();
        }

        return result;
    }

    /**
     * 解压zip文件
     *
     * @param multipartFile zip文件
     * @return 解压后的文件
     */
    public static List<File> unZipFile(MultipartFile multipartFile) {
        List<File> list = Lists.newArrayList();
        File file = multipartFileToFile(multipartFile);
        list.add(file);
        if (multipartFile.getOriginalFilename().endsWith(ZIP)) {
            try {
                ZipFile zipFile = new ZipFile(file, "GBK");
                for (Enumeration entries = zipFile.getEntries(); entries.hasMoreElements(); ) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    File f = new File(System.getenv("CATALINA_TMPDIR") + File.separator + "unZipFiles" + File.separator + entry.getName());
                    logger.info("f:"+f);
                    if (entry.isDirectory()) {
                        f.mkdirs();
                    } else {
                        File parentFile = f.getParentFile();
                        if (!parentFile.exists()) {
                            parentFile.mkdirs();
                        }
                        InputStream in = zipFile.getInputStream(entry);
                        OutputStream out = new FileOutputStream(f);
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                        out.flush();
                        out.close();
                        in.close();
                        list.add(f);
                    }
                }
                zipFile.close();
            } catch (IOException e) {
                ExceptionUtil.getErrorMessage(e);
            }
        } else {
            logger.info("上传文件不是zip文件，默认不予处理。");
        }
        return list;
    }

    /**
     * 清空解压暂存文件
     */
    public static void clearTempFile(MultipartFile multipartFile) {
        File file = new File(System.getenv("CATALINA_TMPDIR") + File.separator + multipartFile.getOriginalFilename());
        file.delete();
        File dir = new File(System.getenv("CATALINA_TMPDIR") + File.separator + "unZipFiles");
        if (dir.exists() && dir.isDirectory()){
            for (File f : dir.listFiles()) {
                if(f.isFile() && f.exists()){
                    f.delete();
                }
            }
        }
    }

    /**
     * multipartFile转file
     *
     * @param multipartFile multipartFile文件
     * @return file文件
     */
    public static File multipartFileToFile(MultipartFile multipartFile) {
        File file = new File(System.getenv("CATALINA_TMPDIR") + File.separator + multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            ExceptionUtil.getErrorMessage(e);
        }
        return file;
    }

    /**
     * file 转 multipartFile
     * @param file file文件
     * @return multipartFile文件
     */
    public static MultipartFile fileToMultipartFile(File file){
        MultipartFile multipartFile = null;
        try {
            multipartFile= new MockMultipartFile(file.getName(), new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return multipartFile;
    }

    public static void main(String[] args) {
        int count = 10;
        while (count > 0) {
            try {
                File tempFile = new File("/home/rdadmin/my.py");
                String str = FileUtil.uploadHermes(tempFile, "my.py").getDescription();
                System.out.println(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            count--;
        }
    }
}
