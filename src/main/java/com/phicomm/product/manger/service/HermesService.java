package com.phicomm.product.manger.service;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * 上传文件的处理逻辑
 * <p>
 * Created by yufei.liu on 2017/7/5.
 */
@Service
public class HermesService {

    private static final String HERMES_FILE_HTTP_URL_PREFIX = "http://114.141.173.17:2580/hermes/file/";
    private static final String HERMES_IMAGE_HTTP_URL_PREFIX = "http://114.141.173.17:2580/hermes/image/";
    private static final String HERMES_FILE_HTTPS_URL_PREFIX = "https://ihome.phicomm.com:2580/hermes/file/";
    private static final String HERMES_IMAGE_HTTPS_URL_PREFIX = "https://ihome.phicomm.com:2580/hermes/image/";
    private static final String HERMES_FILE_HTTP_INNER_URL_PREFIX = "https://192.168.40.40:8080/hermes/file/";

    private File hermesTempDir;

    @Autowired
    public HermesService() {
        String hermesTempDirPath = System.getenv().get("CATALINA_TMPDIR");
        if (Strings.isNullOrEmpty(hermesTempDirPath)) {
            throw new Error();
        }
        hermesTempDir = new File(hermesTempDirPath);
        if (!hermesTempDir.exists()) {
            boolean success = hermesTempDir.mkdirs();
            if (!success) {
                throw new Error();
            }
        }
        if (!hermesTempDir.isDirectory()) {
            throw new Error();
        }
    }

    /**
     * 上传文件
     * 1、计算文件的hash值
     * 2、上传文件到hermes
     *
     * @param file 文件
     * @return 信息
     */
    public Map<String, Object> uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException();
        }
        Map<String, Object> result = Maps.newHashMap();
        String id = UUID.randomUUID().toString().replace("-", "");
        File tempFile = new File(hermesTempDir, id);
        file.transferTo(tempFile);
        // 基本信息
        long fileSize = file.getSize();
        String originalFileName = file.getOriginalFilename();
        result.put("fileSize", fileSize);
        result.put("originalFileName", originalFileName);
        // hash值
        String md5 = FileUtil.md5(tempFile);
        String sha256 = FileUtil.sha256(tempFile);
        result.put("md5", md5);
        result.put("sha256", sha256);
        // 上传hermes
        CommonResponse commonResponse = FileUtil.uploadHermes(tempFile, originalFileName);
        String fileHttpUrl = HERMES_FILE_HTTP_URL_PREFIX + commonResponse.getDescription();
        String imageHttpUrl = HERMES_IMAGE_HTTP_URL_PREFIX + commonResponse.getDescription();
        String fileHttpsUrl = HERMES_FILE_HTTPS_URL_PREFIX + commonResponse.getDescription();
        String imageHttpSUrl = HERMES_IMAGE_HTTPS_URL_PREFIX + commonResponse.getDescription();
        String fileHttpInnerUrl = HERMES_FILE_HTTP_INNER_URL_PREFIX + commonResponse.getDescription();
        result.put("fileHttpUrl", fileHttpUrl);
        result.put("imageHttpUrl", imageHttpUrl);
        result.put("fileHttpsUrl", fileHttpsUrl);
        result.put("imageHttpsUrl", imageHttpSUrl);
        result.put("fileHttpInnerUrl", fileHttpInnerUrl);
        // 删除临时文件
        boolean delete = tempFile.delete();
        if (!delete) {
            throw new IOException();
        }
        return result;
    }

}
