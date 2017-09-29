package com.phicomm.product.manger.module.watchplate;

import java.io.File;
import java.util.List;

/**
 * Created by xiang.zhang on 2017/9/6.
 */
public interface WatchPlatePicture {
    
    /**
     * 保存表盘图片到文件服务器
     * @param
     * @return key
     */
    String saveFileToPicture(File file);

    /**
     * 从临时文件服务器获取已经上传的表盘图片路径，如果没有则返回null
     * @param key
     * @return 如果不存在，则返回null
     */
    List<String> getDownloadUrl(String key);
}
