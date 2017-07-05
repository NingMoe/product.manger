package com.phicomm.product.manger.service;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * 上传文件的处理逻辑
 * <p>
 * Created by yufei.liu on 2017/7/5.
 */
@Service
public class HermesService {

    /**
     * 上传文件
     * 1、计算文件的hash值
     *
     * @param file 文件
     * @return 信息
     */
    public Map<String, Object> uploadFile(MultipartFile file) throws IOException {
        System.out.println(file.isEmpty());
        System.out.println(file.getBytes().length);
        System.out.println(file.getSize());
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        return Maps.newHashMap();
    }

}
