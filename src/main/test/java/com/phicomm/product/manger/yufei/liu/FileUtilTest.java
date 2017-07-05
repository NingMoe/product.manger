package com.phicomm.product.manger.yufei.liu;

import com.phicomm.product.manger.utils.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 文件hash测试
 * <p>
 * Created by yufei.liu on 2017/7/5.
 */
public class FileUtilTest {

    @Test
    public void hashTest() {
        File file = new File("C:\\Users\\yufei.liu\\Desktop\\test.jar");
        System.out.println(FileUtil.md5(file));
        System.out.println(FileUtil.sha256(file));
    }

    @Test
    public void hermesTest() throws IOException {
        File file = new File("C:\\Users\\yufei.liu\\Desktop\\test.jar");
        FileUtil.uploadHermes(file, "test.jar");
    }

}
