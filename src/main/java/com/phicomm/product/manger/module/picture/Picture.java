package com.phicomm.product.manger.module.picture;

import java.io.File;
import java.util.List;

/**
 * Created by xiang.zhang on 2017/9/6.
 */
public interface Picture {
    
    /**
     * 保存文件到本地picture文件夹
     *
     * @param
     * @return key
     */
    String saveFileToPicture(File file);

    /**
     * 从本地picture文件中获取已经上传的文件路径，如果没有则返回null
     * @param
     * @return 如果文件没有上传则返回null，上传文件返回下载链接列表
     */
    List<String> getDownloadUrl(String key);
}
