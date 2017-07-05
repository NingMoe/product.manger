package com.phicomm.product.manger.module.hermes;

import java.io.File;
import java.util.List;

/**
 * 接口
 * Created by yufei.liu on 2017/6/6.
 */
public interface Hermes {

    /**
     * 获取hermes的根
     */
    File getHermesRoot();

    /**
     * 保存文件到本地hermes文件夹
     *
     * @param file 文件
     * @return key
     */
    String saveFileToHermes(File file);

    /**
     * 从本地hermes文件中获取已经上传的文件路径，如果没有则返回null
     * @param key 文件凭据
     * @return 如果文件没有上传则返回null，上传文件返回下载链接列表
     */
    List<String> getDownloadUrl(String key);

}
